# -*- coding:utf8 -*-

class Const(object):
    WeekMonday = 0
    WeekTuesday = 1
    WeekWednesday = 2
    WeekTursday = 3
    WeekFriday = 4
    WeekSaturday = 5
    WeekSunday = 6

    Rest_Normal = 0
    Rest_Single = 1

    # 请假类别
    LeaveTypes = {1: u"事假",
                  2: u"病假",
                  3: u"其它非带薪假",
                  4: u"其它带薪假",
                  5: u"探亲假",
                  6: u"年假",
                  7: u"婚假",
                  8: u"产假及看护假",
                  9: u"哺乳假",
                  10: u"丧假",
                  11: u"儿童陪护假",
                  12: u"带薪病假",
                  13: u"调休"}
    Leave_ShiJia = 1
    Leave_BingJia = 2
    Leave_QiTaFeiDaiXinJia = 3
    Leave_QiTaDaiXinJia = 4
    Leave_TanQin = 5
    Leave_NianJia = 6
    Leave_HunJia = 7
    Leave_ChanJia = 8
    Leave_PuRuJia = 9
    Leave_SangJia = 10
    Leave_ErTongPeiHuJia = 11
    Leave_DaiXinBingJia = 12
    Leave_TiaoXiu = 13


import datetime
import calendar


class MyCalendar(object):
    @classmethod
    def get_1st_of_this_month(cls, date):
        "依据日期返回所在月的第一天"
        return datetime.date(date.year, date.month, 1)

    @classmethod
    def get_1st_of_next_month(cls, date):
        return cls.get_last_date(date) + datetime.timedelta(days=1)

    @classmethod
    def get_last_date(cls, date):
        "依据日期返回所在月的最后一天"
        return datetime.date(date.year, date.month, cls.get_last_day(date.year, date.month))

    @classmethod
    def get_last_date_of_last_month(cls, date):
        "依据日期返回上个月的最后一天"
        return cls.get_1st_of_this_month(date) - datetime.timedelta(days=1)

    @classmethod
    def get_1st_week(cls, date):
        "返回指定年月的第一天是周几"
        return calendar.monthrange(date.year, date.month)[0]

    @classmethod
    def get_last_day(cls, year, month):
        "返回指定年月的最后一天"
        return calendar.monthrange(year, month)[1]

    @classmethod
    def is_weekend(cls, weekday):
        return weekday == Const.WeekSaturday or weekday == Const.WeekSunday

    @classmethod
    def is_workday(cls, weekday):
        return weekday != Const.WeekSaturday and weekday != Const.WeekSunday
