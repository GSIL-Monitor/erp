# -*- coding:utf8 -*-



file_a = '/Users/shiqiangguo/temp/a.xlsx'
file_kaoqing = '/Users/shiqiangguo/temp/11_kaoqing.xlsx'

from openpyxl.reader.excel import load_workbook


wb=load_workbook(file_kaoqing)

wb.get_named_ranges()

ws = wb.active

def R(c_idx , r_idx ):
	return "%s%s"%(c_idx,r_idx)

class CheckLog(object):

	def __init__(self,row):
		self.logid=row[0].value
		self.clock1 = row[1].value
		self.clock2 = row[2].value

	def __str__(self):
		return "%s\t%s\t%s"%(self.logid,self.clock1,self.clock2)

	def __repr__(self):
		return self.__str__()

a1=None

c_idx=['A','B','C']
check_log_dict = {}
for row in ws.rows:
	log = CheckLog(row)
	if log.logid in check_log_dict :
		check_log_dict[ log.logid ].append( log )
	else:
		check_log_dict[ log.logid ] = [ log ]
	break;

print "处理完毕." , check_log_dict

