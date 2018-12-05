# -*- coding:utf8 -*-

from commons import Const, MyCalendar
import datetime


class WorkDay(object):
    def __init__(self, year, month):

        # 双休的周六工作日
        self.sat_of_double_rest_work_days = set(
            [datetime.date(2017, 12, 9) + datetime.timedelta(days=i) for i in range(0, 1000, 14)])

        # 第一天日期
        self.firstdate = datetime.date(year, month, 1)
        self.lastdate = MyCalendar.get_last_date(self.firstdate)

        adate = datetime.date(year, month, 1)

        # 周六的天数
        self.saturday_day_count = 0;
        # 周日的天数
        self.sunday_day_count = 0;

        # 双休工作制的工作天数
        self.workdayCountOfNormal = 0

        # 单休工作制的工作天数
        self.workdayCountOfSingleRest = 0

        while (adate <= self.lastdate):
            if adate.weekday() == Const.WeekSaturday:
                self.saturday_day_count += 1

            if adate.weekday() == Const.WeekSunday:
                self.sunday_day_count += 1

            # 大小周的工作天数
            if adate in self.sat_of_double_rest_work_days \
                    or MyCalendar.is_workday(adate.weekday()):
                self.workdayCountOfNormal += 1

            adate += datetime.timedelta(days=1)
            pass

        # 单休的休息天数
        self.restDayCountOfSingle = max(self.sunday_day_count, self.saturday_day_count)
        # 单休的工作天数
        self.workdayCountOfSingleRest = self.lastdate.day - self.restDayCountOfSingle
        pass

    def isShouldWork(self, adate):
        "大小周工作制的，是否这一天应该上班"
        return adate in self.sat_of_double_rest_work_days \
               or MyCalendar.is_workday(adate.weekday())

    def __repr__(self):
        return "月份：%s 周六天数:%s 周日天数:%s 双休工作天数:%s 单休工作天数:%s 单休休息天数:%s" % \
               (self.firstdate.strftime("%Y-%m"),
                self.saturday_day_count,
                self.sunday_day_count,
                self.workdayCountOfNormal,
                self.workdayCountOfSingleRest,
                self.restDayCountOfSingle)


class SalaryAdjustRecord(object):
    workcode = None  # 工号
    lastname = None  # 姓名
    adjustBefore = None
    adjustAfter = None
    effectDate = None

    def init(self, sheet, rowIdx):
        self.workcode = sheet["A%s" % rowIdx].value
        self.lastname = sheet["B%s" % rowIdx].value
        self.adjustBefore = sheet["C%s" % rowIdx].value
        self.adjustAfter = sheet["D%s" % rowIdx].value
        self.effectDate = sheet["E%s" % rowIdx].value
        if type(self.effectDate) == datetime.datetime:
            self.effectDate = self.effectDate.date()
        else:
            self.effectDate = datetime.datetime.strptime(self.effectDate, "%Y-%m-%d").date()

    def __repr__(self):
        return u"%s\t%s\t%s\t%s\t%s" % (
        self.workcode, self.lastname, self.adjustBefore, self.adjustAfter, self.effectDate)


class LeaveRequest(object):
    "0101 请假审批表	formtable_main_32 , "
    liush = None  # 流水号
    shenqr = None  # 申请人
    bum = None  # 部门
    zhiw = None  # 职务
    shenqrq = None  # 申请日期
    qingjlb = None  # 请假类别
    qingjsy = None  # 请假事由
    xiangglc = None  # 相关流程
    xianggfj = None  # 相关附件
    kaisrq = None  # 开始日期
    jiesrq = None  # 结束日期
    kaissj = None  # 开始时间
    jiessj = None  # 结束时间
    lastname = None  # 员工姓名
    workcode = None  # 工号

    def init(self, row):
        self.id = row['id']
        self.liush = row['liush']
        self.shenqr = row['shenqr']
        self.bum = row['bum']
        self.zhiw = row['zhiw']
        self.shenqrq = row['shenqrq']
        self.qingjlb = row['qingjlb']
        self.qingjsy = row['qingjsy']
        self.xiangglc = row['xiangglc']
        self.xianggfj = row['xianggfj']
        self.kaisrq = datetime.datetime.strptime(row['kaisrq'], "%Y-%m-%d").date()
        self.jiesrq = datetime.datetime.strptime(row['kaisrq'], "%Y-%m-%d").date()
        self.kaissj = datetime.datetime.strptime(row['kaissj'], "%H:%M").time()
        self.jiessj = datetime.datetime.strptime(row['jiessj'], "%H:%M").time()

        self.lastname = row['lastname']
        self.workcode = int(row['workcode'])

    def __repr__(self):
        return u"id:%s\t%s\t%s\t%s\t%s" % (
        self.id, self.workcode, self.lastname, Const.LeaveTypes[self.qingjlb], self.qingjsy)


class Salary(object):
    lastname = ""  # 姓名
    idCard = None  # 身份证
    # 不需要报表
    workid = None  # 工号
    dept_bu = None  # 事业部
    dept_sales = None  # 营销部
    dept_name = None  # 部门
    job_title = None  # 岗位
    job_level = None  # 岗位层级

    # 不需要报表
    adjust_date = None  # 调整日期（调薪 调职位）
    # 基本薪资
    salary_base_adjust_before = 0
    salary_base_adjust_after = 0

    # 应该每日的薪资-- 可能不需要
    # salary_daily_should_adjust_before = 0
    # salary_daily_should_adjust_after = 0

    # 实际的每日的薪资 -- 可能不需要
    # salary_daily_actual_adjust_before = 0
    # salary_daily_actual_adjust_after = 0

    # 应出勤天数
    workday_should_adjust_before = 0;
    workday_should_adjust_after = 0;

    # 实际出勤天数
    workday_actual_adjust_before = 0;
    workday_actual_adjust_after = 0;
    workday_actual_sum = 0

    # 加班天数
    workday_extra_adjust_before = 0
    workday_extra_adjust_after = 0
    workday_extra_sum = 0

    # 工资总额
    salary_total = 0

    # 加班工资
    salary_extra = 0
    # 岗位工资
    salary_title = 0

    # 补贴
    salary_allowance = 0

    # 绩效奖金
    salary_bonus_kpi = 0
    # 其他奖金
    salary_bonus_other = 0

    # 请假天数
    leavedays_adjust_before = 0
    leavedays_adjust_after = 0
    leavedays_sum = 0

    # 请假扣款
    deduct_leave = 0

    # 旷工天数
    absentday_adjust_before = 0;
    absentday_adjust_after = 0;
    absentday_sum = 0

    # 旷工扣款
    deduct_absent = 0

    # 迟到扣款
    deduct_later = 0
    # 其他扣款
    deduct_other = 0
    # 应发工资
    salary_should = 0
    # 养老保险
    insurance_endowment = 0
    # 失业保险
    insurance_unemployment = 0
    # 医疗保险
    insurance_medicare = 0
    # 代扣社会保险
    deduct_insurance_social = 0
    # 公积金
    provident_fund = 0
    # 个人所得税
    individual_income_tax = 0
    # 含税工资
    salary_include_tax = 0
    # 实发工资
    salary_finally = 0

    # -------------  以上为 报表需要的数据 -----------

    # 总工作小时数量
    hours_work_total = 0;
    # 正常工作小时数
    hours_work_normal = 0;
    # 加班小时数
    hours_work_extra = 0;
    # 缺席小时数
    hours_absent = 0;
    # 迟到次数
    later_times = 0
    # 迟到天数
    later_dates = []

    def __init__(self,startDate, oaUser, checklogs, salaryAdjustRecord, leaveRequest):
        self.startDate = startDate
        self.oaUser = oaUser
        self.checklogs = checklogs
        self.salaryAdjustRecord = salaryAdjustRecord
        self.leaveRequest = leaveRequest
        self.workDay = WorkDay(startDate.year, startDate.month)
        pass

    def rebuildChecklogs(self,adate):
        raise ValueError(u"尚未实现重建方法!")


class SalaryKefu(Salary):

    def __init__(self,startDate, oaUser, checklogs, salaryAdjustRecord, leaveRequest ):


        pass

    def rebuildChecklogs(self):

        pass


class SalaryZhuli(Salary):
    pass

def oneday():
    return datetime.timedelta(days=1)


class SalaryNormal(Salary):
    def __init__(self, startDate, oaUser, checklogs, salaryAdjustRecord, leaveRequest):
        Salary.__init__(self, startDate, oaUser, checklogs, salaryAdjustRecord, leaveRequest)
        map_checklogs = {}
        for c in checklogs:
            workTimeEveryDay = map_checklogs.get(c.checkdate)
            dt_key = c.logtime.date()
            if c.logtime.time() < datetime.time(9,30):
                dt_key = c.logtime - oneday()
                workTimeEveryDay = map_checklogs.get(c.checkdate)

            if not workTimeEveryDay:
                workTimeEveryDay = WorkTimeEveryDay(dt_key , datetime.time(9,00), datetime.time(18,30))

            workTimeEveryDay.addLog(c.checkdate , c.checktime)


        while (adate <= self.lastDate):
            self.rebuildChecklogs()

            if self.workDay.isShouldWork(adate):
                # 如果这一天应该上班

                pass
            # 正常员工的情况



            adate = adate + datetime.timedelta(days=1)

    pass

class WorkTimeEveryDay(object):
    def __init__(self,workdate , onTime_should ,offTime_should):
        #日期
        self.workdate = workdate

        self.workOnTime_should = onTime_should
        self.workOffTime_should = offTime_should

        #上班时间
        self.workOnTime = None
        #下班时间
        self.workOffTime = None

        #请假
        self.isAbsentHasReason = False
        #旷工
        self.isAbsentNoReason = False
        #迟到
        self.isWorkOnTimeLater = True
        #早退
        self.isWorkOffTimeEarly = True

    def setWorkOffTime(self, workOffTime):
        if not self.workOffTime or self.workOffTime < workOffTime:
            self.workOffTime = workOffTime

    def setWorkOnTime(self , workOnTime):
        if workOnTime >= self.workOffTime_should:
            self.setWorkOffTime(workOnTime)
        else:
            self.workOnTime = workOnTime

    def addLog(self,checkdate , checktime):

        # 跨天打卡情况
        if checkdate > self.workdate :
            self.setWorkOffTime(checktime)
            return

        # 尚未有上班打卡记录时，默认第一次打卡认为上班打卡
        if not self.workOnTime:
            self.setWorkOnTime( checktime )
            return

        # 尚未有下班打卡时间
        if not self.workOffTime:
            self.setWorkOffTime( checktime )







class OaUser(object):
    User_State_probationary = 0  # ("试用"),
    User_State_Official = 1  # ("正式"),
    User_State_Temporary = 2  # ("临时"),
    User_State_deferred = 3  # ("试用延期"),
    User_State_Dismissal = 4  # ("解聘"),
    User_State_Dimission = 5  # ("离职"),
    User_State_Retire = 6  # ("退休"),
    User_State_Invalid = 7  # ("无效")
    # 最大的可用用户id
    User_State_Max_Usable = User_State_deferred

    singleRestTitle = set(['业务助理', '客服专员', '越南客服', '中级客服专员', '客服专员（华语）'])

    def __init__(self, row):
        self.id = row['id']
        self.loginid = row['loginid']
        self.password = row['password']
        self.lastname = row['lastname']
        self.workcode = row['workcode']
        self.userstatus = row['userstatus']
        self.jobtitlename = row['jobtitlename']
        self.deptid = row['deptid']
        self.departmentname = row['departmentname']
        self.subcompanyid1 = row['subcompanyid1']
        self.departmentcode = row['departmentcode']
        self.tlevel = row['tlevel']

    def loadCheckinout(self, checklogs):
        pass

    def loadCheckinout_cs(self, checklogs):
        first_class_in_time = datetime.time(9, 30)
        first_class_out_time = datetime.time(9, 30)
        second_class_in_time = datetime.time(9, 30)
        second_class_out_time = datetime.time(9, 30)

    def isSingleRest(self):
        return self.jobtitlename in self.singleRestTitle

    def __repr__(self):
        return u"%s\t%s\t%s" % (self.workcode, self.lastname, self.jobtitlename)


class Checkinout(object):
    def __init__(self, row):
        self.workcode = int(row['pin'])
        self.checkdatetime = row['checktime']
        self.checkdate = self.checkdatetime.date()
        self.checktime = self.checkdatetime.time()
        self.sn_name = row['sn_name']

    def __cmp__(self, other):
        return cmp(self.checktime, other.checktime)

    def __repr__(self):
        return "%s - %s" % (self.workcode, self.checktime.strftime("%Y-%m-%d %H:%M:%S"))
