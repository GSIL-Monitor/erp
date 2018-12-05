# -*- coding:utf8 -*-

import Logger
from staff_checkinout import *
from commons import MyCalendar
import logging
import datetime

from sqlserver_dao import OaDao , ZktecoDao
from model import *
from XlsReadDao import SalaryAdjustDao

logger = logging.getLogger()

salary_adjust_fn = "调薪表.xlsx"

class Salary(object):


    def __init__(self,year,month):
        self.year = year
        self.month = month
        self.firstDate = datetime.date(year, month, 1)
        self.lastDate = MyCalendar.get_last_date(self.firstDate)
        self.oaDao = OaDao()
        self.zktecoDao = ZktecoDao()
        self.salaryAdjustDao = SalaryAdjustDao()
        self.workDay = WorkDay(year,month)

        logger.info(u"准备计算薪资，年:%s-%s" , year,month)

    def start(self):
        allUsers = self.oaDao.getAllUser()
        checklogMapWorkcode = self.zktecoDao.getAllChecklogMapWorkcode(self.firstDate)

        leaveRequestMap = self.oaDao.getLeaveRequest(self.firstDate)

        salaryAdjustRecordMap = self.salaryAdjustDao.getAllSalaryAdjustRecords(salary_adjust_fn)

        for oaUser in allUsers:
            code=oaUser.workcode
            checklogs = checklogMapWorkcode.get(code)
            if code not in checklogs :
                logger.info("没有找到打卡记录：%s" , oaUser)
                continue
            if code not in salaryAdjustRecordMap:
                logger.info("没有找到调薪记录:%s" , oaUser)
                continue
            leaveRequest = leaveRequestMap.get(code) if code in leaveRequestMap else []

            salaryAdjustRecord =  salaryAdjustRecordMap.get(code)
            self.calcOne(oaUser,checklogs,salaryAdjustRecord , leaveRequest)

        pass

    def calcOne(self,oaUser , checklogs , salaryAdjustRecord,leaveRequest):
        adate = self.firstDate;
        #以日期为key，将同一天的打卡记录放到一起
        map_checklogs = {}
        aday_end_time = datetime.time(4)

        while (adate <= self.lastDate):
            adate = adate + datetime.timedelta(days=1)

        for a in checklogs:
            # Checkinout
            key = a.checkdate
            if a.checktime > datetime.time(0) and a.checktime < datetime.time(4):
                key = a.checkdate - datetime.timedelta(days=1)

            if key in map_checklogs :
                map_checklogs[key].append(a)
            else:
                map_checklogs[key] = [ a ]

        while(adate<=self.lastDate):
            one_day_checklog_lst = map_checklogs.get(adate)
            if oaUser.singleRestTitle:
                #单休的处理


                pass
            else:
                #双休的处理
                if self.workDay.isShouldWork(adate):
                    if one_day_checklog_lst :
                        #有打卡记录
                        one_day_checklog_lst = sorted(one_day_checklog_lst)
                        log0 = one_day_checklog_lst[0]
                        on_time=log0.checktime
                        if log0.checktime > datetime.time(18,30):
                            pass
                        pass
                    else:
                        #无打卡记录
                        pass
                else:
                    #计算加班工资
                    pass
                pass
            #下一天
            adate = adate + datetime.timedelta(days=1)
        pass



if __name__ == '__main__':

    lastDay = MyCalendar.get_last_date_of_last_month( datetime.date.today() )
    logger.info("start calc")
    salary = Salary(lastDay.year , lastDay.month)

    salary.start()

