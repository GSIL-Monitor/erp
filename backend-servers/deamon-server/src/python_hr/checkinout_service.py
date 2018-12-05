# -*- coding:utf8 -*-

import datetime

class CheckinoutCustomerService(object):
    """客服人员的考勤情况"""

    def __init__(self , oaUser , checklogs ):
        first_class_in_time = datetime.time(9, 30)
        first_class_out_time = datetime.time(9, 30)
        second_class_in_time = datetime.time(9, 30)
        second_class_out_time = datetime.time(9, 30)

class CheckinoutOtherService(object):

    def __init__(self , oaUser , checkLogs):
        in_time_rule = datetime.time(9,30)
