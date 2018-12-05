# -*- coding:utf8 -*-

import datetime
import calendar




class StaffDao(object):
    def __init__(self):
        self.staffs = {}
        s = Staff()
        s.workid = 669
        s.loginid = "shiqiangguo"
        s.title = "首席架构师"

        s.dept_id = 1
        s.dept_name = "技术部"
        s.adjust_date = datetime.date(2017, 11, 13)
        s.salary_base_adjust_before = 3000
        s.salary_base_adjust_after = 5000

        self.staffs[s.workid] = s

        s = Staff()
        s.workid = 700
        s.loginid = "weizhi1"
        s.title = u"广告助理"
        s.dept_id = 2
        s.dept_name = u"事业一部/一营/一组"
        s.adjust_date = datetime.date(2017, 11, 23)
        s.salary_base_adjust_before = 5000
        s.salary_base_adjust_after = 10000

        self.staffs[s.workid] = s

        s = Staff()
        s.workid = 701
        s.loginid = "weizhi2"
        s.title = u"广告助理"
        s.dept_id = 2
        s.dept_name = u"事业一部/一营/二组"
        s.adjust_date = None
        s.salary_base_adjust_before = 5000
        s.salary_base_adjust_after = 10000

        self.staffs[s.workid] = s
        pass

    def getStaff(self, id):
        # 依据员工id， 返回员工信息
        return self.staffs[id]



class Staff(object):
    single_rest_titles = set([u"广告助理", u"客服专员"])

    def __init__(self):
        self.workid = None
        self.loginid = None
        self.name = None
        self.title = None
        self.dept_id = None
        self.dept_name = None
        self.is_single_rest = None

        #调薪日期
        self.adjust_date = None

        #基本薪资
        self.salary_base_adjust_before = 0
        self.salary_base_adjust_after = 0
        
        #应该每日的薪资
        self.salary_daily_should_adjust_before = 0
        self.salary_daily_should_adjust_after = 0

        #实际的每日的薪资
        self.salary_daily_actual_adjust_before = 0
        self.salary_daily_actual_adjust_after = 0
        
        #应该工作的天数
        self.workday_should_adjust_before = 0;
        self.workday_should_adjust_after = 0;

        # 实际工作的天数
        self.workday_actual_adjust_before = 0;
        self.workday_actual_adjust_after = 0;

        #总工作小时数量
        self.hours_work_total = 0;
        #正常工作小时数
        self.hours_work_normal = 0;
        #加班小时数
        self.hours_work_extra = 0;
        #缺席小时数
        self.hours_absent = 0;
        #迟到次数
        self.later_times=0
        #迟到天数
        self.later_dates=[]
        pass

    def init(self):
        self.is_single_rest= self.title in self.single_rest_titles

    def calc_work_info(self, workDay, checklogs):

        checklogs = [
            Checklog(1, datetime.datetime(2017, 12, 3, 9)),
            Checklog(1, datetime.datetime(2017, 12, 3, 18)),
            Checklog(1, datetime.datetime(2017, 12, 1, 9)),
            Checklog(1, datetime.datetime(2017, 12, 1, 18)),
            Checklog(1, datetime.datetime(2017, 12, 2, 9)),
            Checklog(1, datetime.datetime(2017, 12, 2, 18)),
        ]
        #调薪日期如果为空，则认为本月最后一天的下一天调薪
        tmp_adjust_date = workDay.lastdate + datetime.timedelta(days=1) if self.adjust_date==None else self.adjust_date

        #每日打卡记录列表，以天未key ，将每日打卡的记录放到一个列表中
        map_checklogs = {}
        for c in checklogs:
            t = datetime.datetime.now()
            c.logtime = t;
            lst = map_checklogs.get(c.logtime.date())
            if lst:
                lst.append( c )
            else:
                map_checklogs[c.logtime.date()] = [ c ]


        adate = workDay.firstdate;
        #从本月第一天开始，一直算到本月最后一天
        while adate <= workDay.lastdate:

            this_checklogs = map_checklogs.get(adate)




            adate = adate + datetime.timedelta(days=1)
            pass

        pass

    pass




if __name__ == '__main__':
    # print WorkDay(2017, 12)
    # print WorkDay(2017, 11)
    # print WorkDay(2017, 10)
    # print WorkDay(2017, 9)

    checklogs1 = [
        Checklog(1, datetime.datetime(2017, 12, 3, 9)),
        Checklog(1, datetime.datetime(2017, 12, 3, 18)),
        Checklog(1, datetime.datetime(2017, 12, 1, 9)),
        Checklog(1, datetime.datetime(2017, 12, 1, 18)),
        Checklog(1, datetime.datetime(2017, 12, 2, 9)),
        Checklog(1, datetime.datetime(2017, 12, 2, 18)),
    ]
    print checklogs1
    checklogs2 = sorted(checklogs1)
    print checklogs2
