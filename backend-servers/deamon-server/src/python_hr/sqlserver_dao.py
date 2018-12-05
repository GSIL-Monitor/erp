# -*- coding:utf8 -*-
import pymssql
import logging

from commons import MyCalendar

from model import OaUser , Checkinout , LeaveRequest



class OaDao(object):

    def __init__(self ,host="192.168.105.234" , user="sa" ,pwd="sqLbgn@220617$" , db="ecology"):
        self.conn = pymssql.connect(host, user, pwd, db)
        self.logger = logging.getLogger()

    def getAllUser(self):
        cursor = self.conn.cursor(as_dict=True)
        sql = """
        SELECT TOP 10 r.id , r.loginid , password , lastname , r.workcode, r.status as userstatus,
        j.jobtitlename , 
        d.id deptid, d.departmentname , d.subcompanyid1 , d.departmentcode,d.tlevel 
        FROM dbo.HrmResource r 
        join dbo.HrmJobTitles j on r.jobtitle=j.id 
        join dbo.HrmDepartment d on r.departmentid=d.id
        where r.status<=%s
        """
        l = cursor.execute(sql%OaUser.User_State_Max_Usable)
        self.logger.info(u"装载oa中的有效用户，共计:%s 条， sql:%s", l, sql)
        all = []
        for row in cursor.fetchall():
            oaUser = OaUser(row)
            logging.info(u"装载到用户:%s",oaUser)
            all.append(oaUser)
        cursor.close()
        return all

    def getLeaveRequest(self , date):
        cursor = self.conn.cursor(as_dict=True)
        firstDay = MyCalendar.get_1st_of_this_month(date).strftime('%Y-%m-%d')
        lastDay = MyCalendar.get_last_date(date).strftime('%Y-%m-%d')

        sql = """select r.lastname,r.workcode,
                f.id,f.requestId,f.liush,f.shenqr,f.bum,f.zhiw,f.shenqrq,f.qingjlb,f.qingjsy,f.xiangglc,f.xianggfj,f.kaisrq,
                f.jiesrq,f.kaissj,f.jiessj,f.qingjrqt,f.nj1,f.nj2,f.kxnjts
                from dbo.formtable_main_32 f join dbo.HRMResource r on f.shenqr=r.id 
                where 
                f.kaisrq BETWEEN '%s' and '%s' or f.jiesrq  BETWEEN '%s' and '%s'
                """%(firstDay , lastDay , firstDay , lastDay)
        self.logger.info(u"查询请假:%s",sql)
        l = cursor.execute(sql)
        self.logger.info(u"装载oa 请假记录，共计:%s 条， sql:%s", l, sql)
        map_all = {}
        for row in cursor.fetchall():
            record = LeaveRequest()
            record.init(row)
            logging.debug(u"装载到请假记录:%s", record)
            logging.debug(u"开始日期:%s %s %s %s" , record.kaisrq , type(record.kaisrq) , record.kaissj , type(record.kaissj))
            if record.workcode in map_all:
                map_all.get(record.workcode).append(record)
            else:
                map_all[record.workcode] = [record]
        cursor.close()
        return map_all

class ZktecoDao(object):
    def __init__(self ,host="192.168.105.234" , user="sa" ,pwd="sqLbgn@220617$" , db="zkteco_database"):
        self.conn = pymssql.connect(host, user, pwd, db)
        self.logger = logging.getLogger()
    def getAllChecklogMapWorkcode(self, firstDay):
        cursor = self.conn.cursor(as_dict=True)
        sql = "SELECT TOP 100 * FROM dbo.checkinout where checktime between '%s' and '%s'"%\
              (firstDay.strftime("%Y-%m-%d") , MyCalendar.get_1st_of_next_month(firstDay).strftime("%Y-%m-%d"))

        l = cursor.execute(sql )
        self.logger.info(u"装载月份：%s的所有打卡记录，共计:%s 条， sql:%s", firstDay.strftime("%Y-%m"), l , sql)
        map_all = {}
        for row in cursor.fetchall():
            record = Checkinout(row)
            logging.info(u"装载到打卡记录:%s", record)
            if record.workcode in map_all:
                map_all.get(record.workcode).append(record)
            else:
                map_all[ record.workcode] = [record]

        cursor.close()
        return map_all;