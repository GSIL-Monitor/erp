@echo off
color 0A
title ���������������ã�HOST�ļ��޸���
@echo ########################################
@echo "��ע�����ɱ�������ʾ��һ��Ҫ����"
@echo ########################################
if not exist C:\Windows\system32\drivers\etc\hosts.bak\hosts (goto bakhost) else (goto process);

:process
@echo ���������Hosts�ļ����޸�Ȩ�ޡ�
@attrib "%windir%\System32\Drivers\etc\hosts" -r -a -s -h
@echo ------------------------------------------------------------------
@echo Hosts�ļ��޸�Ȩ���ѿ�������ʼ�޸�Hosts�ļ����밴����ѡ��
@echo ------------------------------------------------------------------
@cd.> %systemroot%\system32\drivers\etc\hosts
@echo # Copyright (c) 1993-2009 Microsoft Corp.>>%systemroot%\system32\drivers\etc\hosts
@echo #>>%systemroot%\system32\drivers\etc\hosts
@echo # This is a sample HOSTS file used by Microsoft TCP/IP for Windows.>>%systemroot%\system32\drivers\etc\hosts
@echo #>>%systemroot%\system32\drivers\etc\hosts
@echo # This file contains the mappings of IP addresses to host names. Each>>%systemroot%\system32\drivers\etc\hosts
@echo # entry should be kept on an individual line. The IP address should>>%systemroot%\system32\drivers\etc\hosts
@echo # be placed in the first column followed by the corresponding host name.>>%systemroot%\system32\drivers\etc\hosts
@echo # The IP address and the host name should be separated by at least one>>%systemroot%\system32\drivers\etc\hosts
@echo # space.>>%systemroot%\system32\drivers\etc\hosts
@echo #>>%systemroot%\system32\drivers\etc\hosts
@echo # Additionally, comments (such as these) may be inserted on individual>>%systemroot%\system32\drivers\etc\hosts
@echo # lines or following the machine name denoted by a '#' symbol.>>%systemroot%\system32\drivers\etc\hosts
@echo #>>%systemroot%\system32\drivers\etc\hosts
@echo # For example:>>%systemroot%\system32\drivers\etc\hosts
@echo #>>%systemroot%\system32\drivers\etc\hosts
@echo #      102.54.94.97     rhino.acme.com          # source server>>%systemroot%\system32\drivers\etc\hosts
@echo #       38.25.63.10     x.acme.com              # x client host>>%systemroot%\system32\drivers\etc\hosts
@echo #>>%systemroot%\system32\drivers\etc\hosts
@echo # localhost name resolution is handled within DNS itself.>>%systemroot%\system32\drivers\etc\hosts
@echo #	127.0.0.1       localhost>>%systemroot%\system32\drivers\etc\hosts
@echo #	::1             localhost>>%systemroot%\system32\drivers\etc\hosts

@echo 1���������ػ��� 127.0.0.1 (����1)
@echo 2�����Ի��� 192.168.105.135(����2)
@echo 3����������(����3) 
@echo.>>%systemroot%\system32\drivers\etc\hosts
@echo ------------------------------------------------------------------
SET /p string=��ѡ�񻷾�:
IF "%string%"=="1" goto 1
IF "%string%"=="2" goto 2
IF "%string%"=="3" goto 3


:1
@echo ------------------------------------------------------------------
@echo ѡ���˱��ؿ�������������URL��ָ������127.0.0.1��
@echo 127.0.0.1 erp-front-local.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 127.0.0.1 erp-admin-local.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 127.0.0.1 erp-product-local.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 127.0.0.1 erp-orders-local.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 127.0.0.1 erp-purchase-local.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 127.0.0.1 erp-store-local.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 127.0.0.1 erp-tms-local.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 127.0.0.1 erp-deamon-local.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 127.0.0.1 erp-finance-local.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 127.0.0.1 erp-img-local.stosz.com>>%systemroot%\system32\drivers\etc\hosts


goto end


:2
@echo ------------------------------------------------------------------
@echo  ѡ����135 ����(���ɿ�������)�����е�urlָ���� 192.168.105.135
@echo 192.168.105.135 luckydog-erp-front-dev.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.135 luckydog-erp-admin-dev.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.135 luckydog-erp-product-dev.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.135 luckydog-erp-order-dev.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.135 luckydog-erp-purchase-dev.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.135 luckydog-erp-store-dev.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.135 luckydog-erp-tms-dev.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.135 luckydog-erp-deamon-dev.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.135 luckydog-erp-finance-dev.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.135 luckydog-erp-img-dev.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.135  www.fuppg.com>>%systemroot%\system32\drivers\etc\hosts



goto end

:3
@echo ------------------------------------------------------------------
@echo  ѡ����64 ����(���Ի���)�����е�urlָ���� 192.168.105.64
@echo 192.168.105.64 luckydog-erp-front-test.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.64 luckydog-erp-admin-test.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.64 luckydog-erp-product-test.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.64 luckydog-erp-order-test.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.64 luckydog-erp-purchase-test.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.64 luckydog-erp-store-test.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.64 luckydog-erp-tms-test.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.64 luckydog-erp-deamon-test.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.64 luckydog-erp-finance-test.stosz.com>>%systemroot%\system32\drivers\etc\hosts
@echo 192.168.105.64 luckydog-erp-img-test.stosz.com>>%systemroot%\system32\drivers\etc\hosts


goto end


goto end

:bakhost
@echo  ��ʼ��������HOST�ļ���
@xcopy C:\Windows\system32\drivers\etc\hosts C:\Windows\system32\drivers\etc\hosts.bak\ /d /c /i /y 
@echo Hosts�ļ�������ϡ�
@echo ------------------------------------------------------------------
goto process

:end
@echo ##########
@echo ����������¼�Ѿ������ϣ���ʼ�ر�Hosts�ļ����޸�Ȩ�ޡ�
@attrib "%windir%\System32\Drivers\etc\hosts" +r +a +s +h
@echo ##########
@echo Hosts�ļ����޸�Ȩ�޹رճɹ������������������棬��ر�������������������
@echo ##########
@pause>>nul
@echo ��ȷ��������ѹرգ���������
@echo ##########
@pause>>nul
ipconfig /flushdns
@echo ##########
@echo �������������Ѹ��£�����������˳�������
@echo ------------------------------------------------------------------
@echo.
pause
EXIT


