OSS RDBS know-how
===

## ★ PostgreSQL






## ★ MySQL

### ■　構築

```
yum remove mariadb-libs    #必要ないかも
rpm -ivh http://dev.mysql.com/get/mysql57-community-release-el7-8.noarch.rpm
yum install mysql-community-server

mysqld --version
 mysqld Ver 5.7.19 for Linux on x86_64 (MySQL Community Server (GPL))
```

自動起動設定  
systemctl enable mysqld.service  

起動  
systemctl start mysqld.service  

停止  
systemctl stop mysqld.service  

初期化PWDの確認  
less /var/log/mysqld.log | grep password

MySQL のセキュリティ設定  
mysql_secure_installation  
pwd-->Changeit!=123  

MySQL へのログイン確認  
mysql -u root -p  

設定変更 /etc/my.cnfに以下を追加  
```
character-set-server = utf8
default_password_lifetime = 0
```

DB作成、ユーザ作成  
```
create database testdb;
use testdb;
GRANT ALL PRIVILEGES ON testdb.* to testuser@localhost identified by 'Testuser!=123';
```

コマンドラインにて、sqlファイルの実行  
mysql -utestuser -p testdb < /opt/test/test.sql > result.txt

各種確認、db切替  
```
show databases;
show tables;
show grants for testuser@localhost;
use testdb;
```

Ref   
http://enomotodev.hatenablog.com/entry/2016/09/01/225200

### ■ Export/Import
エクスポート  
mysqldump -u[ユーザー名] -p[パスワード] -r [バックアップファイル名] --single-transaction [データベース名]  
mysqldump -utestuser -p -r testdb.bak --single-transaction testdb

インポート  
mysql -u[ユーザー名] -p[パスワード] [インポートするデータベース名] < [インポートするファイル名]  
```
create database testdbbak;
grant all privileges on testdbbak.* to testuser@localhost;
mysql -utestuser -p testdbbak < testdb.bak
```

### ■　暗号化
[MySQL Enterprise Transparent Data Encryption (TDE)](https://www.mysql.com/jp/products/enterprise/tde.html)
