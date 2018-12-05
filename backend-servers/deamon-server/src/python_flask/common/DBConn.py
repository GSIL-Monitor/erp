# -*- coding:utf8 -*-
import MySQLdb
from MySQLdb.cursors import DictCursor

class DBConn(object):

    #
    # def __init__(self , host="localhost" , user="",passwd="",database=""):
    #     import MySQLdb
    #     from MySQLdb.cursors import DictCursor
    #
    #     self.dbconn = MySQLdb.connect(host=host,user=user , passwd=passwd,db=database,charset="utf8" ,cursorclass=DictCursor)
    #     self.cursor = self.dbconn.cursor()

    def __init__(self,conf):
        print "conf :" , conf
        host=conf.get("host")
        user = conf.get("user")
        passwd=conf.get("passwd")
        database=conf.get("database")
        cursorclass = conf.get("cursorclass") if conf.get("cursorclass") else DictCursor;
        self.dbconn = MySQLdb.connect(host=host, user=user, passwd=passwd, db=database, charset="utf8",
                                      cursorclass=DictCursor)
        self.cursor = self.dbconn.cursor()
        pass

    def execute(self,sql):
        print "sql is ",sql
        self.cursor = self.dbconn.cursor()
        self.cursor.execute(sql)

    def fetchone(self):
        return self.cursor.fetchone()

    def fetchAll(self):
        return self.cursor.fetchall();
    def close(self):
        self.cursor.close();


if __name__ == '__main__':
    conf = {
        "host": "localhost", "user": "root", "passwd": "root", "database": "product"
    }
    #dbconn = DBConn("localhost" , "root" , "root" , "product")
    dbconn = DBConn( **conf )
    #dbconn = DBConn( host= "localhost", user="root", passwd="root", database="product" )
    sql = "select * from product limit 10"

    dbconn.execute(sql)
    row = dbconn.fetchone()
    for k in row:
        print k , row[k] , type(row[k])


