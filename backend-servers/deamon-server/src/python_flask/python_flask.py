# -*- coding:utf8 -*-
from flask import Flask, request, render_template, jsonify
from flask_request_params import bind_request_params
import socket


app = Flask(__name__)
app.secret_key = 'secret'
app.before_request(bind_request_params)

app.config['SECRET_KEY'] = 'Fianna'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root:root@http://localhost/:3306/product'

conf = {
        "host": "localhost", "user": "root", "passwd": "buguniao.com", "database": "product"
    }
localIP = socket.gethostbyname(socket.gethostname())
print("Runing on ip %s" % localIP)
if("192.168.110.156" == localIP):
    app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root:root@http://127.0.0.1/:3306/product'
    conf = {
        "host": "127.0.0.1", "user": "root", "passwd": "99999", "database": "product"
    }

from common.DBConn import DBConn


@app.route('/',methods=['GET', 'POST'])
def hello_world():
    #每次重连. 或者抛异常后重连
    dbconn = DBConn(conf)
    sql = "select no , name from category"

    dbconn.execute(sql)
    cat_rows = dbconn.fetchAll()

    cat_dict = {}
    for row in cat_rows:
        cat_dict[row['no']]=row['name']



    sql="""select p1.id,p1.spu,p1.state,p1.title, p1.create_at , p1.state , p1.state_time,p1.main_image_url ,c.no category_no ,c.name category_title, 
    p2.id p2_id,p2.spu p2_spu,p2.main_image_url dst_main_image_url, ifnull(ps.cnt, 0) cnt
    """

    sql_from="""from product p1 join category c on p1.category_id=c.id join product_same ps on p1.id=ps.src_id 
    left join product p2 on ps.dst_id=p2.id  """
    all_state={
        "waitFill":u"待填充",
        "archiving":u"建档中",
        "onsale":u"已上架",
        "offsale":u"已下架",
        "disappeared":u"已销档"
    }

    all_state_class={
        "waitFill":u"active",
        "archiving":u"warning",
        "onsale":u"success",
        "offsale":u"info",
        "disappeared":u"danger"
    }
    src_id = request.form.get('src_id')
    dst_id = request.form.get('dst_id')
    category_no = request.form.get('category_no')
    state = request.form.getlist("state")
    page_num = request.form.get('page_num')
    page_num = ((page_num == None or page_num < 1) and 1 or int(page_num))
    page_row = request.form.get('page_row')
    page_row = ((page_row == None) and 100 or int(page_row))
    sort_name = request.form.get('sort_name')
    sort_name = ((sort_name == None) and "src_id" or sort_name)
    print(((sort_name == None) and "src_id" or sort_name))
    sort_key = request.form.get('sort_key')
    sort_key = ((sort_key == None) and 1 or int(sort_key))
    sort_keys = ((sort_key % 2 ==0) and "asc" or "desc")

    print "request.params",request.params , request.form
    # print "state:" , state, type(state)
    sql_where=""
    if src_id or dst_id or category_no or state:
        sql_where += " where "
        s = ""
        if src_id:
            s += " and p1.id=" + src_id
        if dst_id:
            s += " and p2.id=" + dst_id
        if category_no:
            s += " and c.no like '" + category_no.strip() + "%'"
        if state:
            s += " and p1.state in ('"+"','".join(state) +"')"
        sql_where += s[4:]

    sql_order = ""
    if sort_keys != None and sort_name != None:
        if sort_name == 'dst_id' :
            sql_order += " order by cnt desc, dst_id " + sort_keys
        else:
            sql_order += " order by " + sort_name + " " + sort_keys

    sql_limit = " limit " + str((page_num - 1) * page_row) + ", " + str(page_num * page_row + page_row)

    sql = sql + sql_from + sql_where + sql_order + sql_limit

    dbconn.execute("select count(1) cnt " + sql_from + sql_where)
    total = dbconn.fetchone();
    total = total["cnt"]
    page_cnt = total / page_row
    if total // page_row > 0 : page_cnt += 1
    print("sql is %s" % sql)
    dbconn.execute(sql)
    all = dbconn.fetchAll();


    cat_name_dict={}
    for r in all:
        cn = r["category_no"]
        s = "";
        for x in range(0,len(cn),2):
            k = cn[0:len(cn)-x]
            s = "->" + cat_dict[k] + s

        s=s[2:]
        cat_name_dict[r["id"]]=s

    dbconn.close();
    return render_template("product/list.html",
                           products=all,request=request,all_state=all_state,
                           cat_name_dict=cat_name_dict , all_state_class=all_state_class , page_count=len(all),
                           page_row=page_row, page_num=page_num,total=total, page_cnt=page_cnt, sort_key=sort_key, sort_name=sort_name)



if __name__ == '__main__':
    app.run(port=8800)
