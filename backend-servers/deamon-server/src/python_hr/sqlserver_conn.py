# -*- coding:utf8 -*-

import pymssql



host="192.168.105.234"


conn = pymssql.connect(host, "sa", "sqLbgn@220617$", "ecology")

cursor = conn.cursor();


sql="SELECT TOP 100 r.id , loginid , password , lastname, j.jobtitlename FROM dbo.HrmResource r join dbo.HrmJobTitles j on r.jobtitle=j.id where loginid='shiqiangguo'";

sql="SELECT TOP 100 * FROM dbo.checkinout where checktime between '2017-08-01' and '2017-09-01' and pin='000000669'"
cursor.execute( sql )

for row in cursor.fetchall():
    print row