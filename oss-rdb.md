OSS RDBS know-how
===

## ★ PostgreSQL

### ■　構築
#### 1.構築済みのpostgresを削除
```
[root@blaze ~]# rpm -qa | grep postgres
postgresql94-contrib-9.4.10-1PGDG.rhel7.x86_64
postgresql94-server-9.4.10-1PGDG.rhel7.x86_64
postgresql94-9.4.10-1PGDG.rhel7.x86_64
postgresql94-devel-9.4.10-1PGDG.rhel7.x86_64
postgresql94-libs-9.4.10-1PGDG.rhel7.x86_64

[root@blaze ~]# yum remove postgresql94-server-9.4.10
......
[root@blaze ~]# yum remove postgresql94-libs-9.4.10

[root@blaze lib]# du -sh /var/lib/pgsql/
56M     /var/lib/pgsql/
[root@blaze lib]# rm -rf /var/lib/pgsql/

[root@blaze ~]# userdel postgres
```

#### 2.PostgreSQL9.5をCentOS7に導入
REF: http://vdeep.net/centos7-postgres  
下記公式サイトから最新版のPostgreSQLのリポジトリURLを取得  
http://yum.postgresql.org/repopackages.php
PostgreSQL 9.5 -- CentOS 7 - x86_64
https://download.postgresql.org/pub/repos/yum/9.5/redhat/rhel-7-x86_64/pgdg-centos95-9.5-3.noarch.rpm

```
[root@blaze ~]# less /etc/redhat-release
CentOS Linux release 7.2.1511 (Core)

--yum repo install and edit
# rpm -iUvh https://download.postgresql.org/pub/repos/yum/9.5/redhat/rhel-7-x86_64/pgdg-centos95-9.5-3.noarch.rpm

# vi /etc/yum.repos.d/pgdg-95-centos.repo
[pgdg95]
...省略...
enabled=1
↓
enabled=0

-- yum install postgresql9.5
# yum -y install --enablerepo=pgdg95 postgresql95-server postgresql95-devel postgresql95-contrib

[root@blaze ~]# psql --version
psql (PostgreSQL) 9.5.9

-- initdb
[root@blaze ~]# /usr/pgsql-9.5/bin/postgresql95-setup initdb
Initializing database ... OK

-- start/stop/status
# systemctl status|start|stop postgresql-9.5

-- passwd
# passwd postgres
```

#### 3. Postgresql初期設定
設定ファイルフォルダ： /var/lib/pgsql/9.x/data/  
外部サーバーからのアクセスを許可するには「/var/lib/pgsql/9.5/data/postgresql.conf」を次のように編集することで設定。デフォルトではローカル（127.0.0.1）のみ許可されています。お好みで設定してください。  
https://www.postgresql.jp/document/9.5/html/auth-pg-hba-conf.html  
PostgreSQLはインストールしただけでは、自ホストからしか接続できず、他のホストから利用できない。  
他ホストから接続するための設定を行う  
 1.postgresq.confファイルの修正し、外部からのアクセスを可能とする（listen_addressesの設定） ```listen_addresses = '*' ```   
 2.iptables/firewalldにて、ポートを開ける(5432)  
 3.接続できるクライアントを設定（pg_hba.conf ==> host)   
TYPE  DATABASE        USER            ADDRESS                 METHOD  
host    all             all           10.217.205.81/32        md5  
METHOD: md5 -pwdハッシュ認証  password -平文　 trust -無条件許可

```
# vi /var/lib/pgsql/9.5/data/postgresql.conf
---外部アクセス許可
# 59行目あたり
#listen_addresses = 'localhost'
↓
listen_addresses = '*'

---クライアントの認証設定
# vi /var/lib/pgsql/9.5/data/pg_hba.conf
#TYPE DATABASE USER   ADDRESS          METHOD
# "local" is for Unix domain socket connections only
#local   all             all                                     peer
local   all             all                                     trust
#add new client by password hash authn.
host   all     all    192.168.0.11/32  md5

---create db USER
#su - postgres
-bash-4.2$ createuser -P testuser
 新しいロールのためのパスワード: testuser00

--- change password for super db user/role (postgres)
 postgres=# alter role postgres password 'postgres00';
 ALTER ROLE

---create DATABASE
-bash-4.2$ psql
postgres=# create database testdb owner testuser;
CREATE DATABASE

--create table
postgres=# \c testdb testuser
データベース "testdb" にユーザ"testuser"として接続しました。
testdb=> create table test ( aa varchar(8), bb varchar(16));
CREATE TABLE

testdb=> \dt
          リレーションの一覧
 スキーマ | 名前 |    型    |  所有者
----------+------+----------+----------
 public   | test | テーブル | testuser
(1 行)
```

### ■　各種確認
設定ファイル  
/var/lib/pgsql/9.x/data/postgresql.conf  

各種確認
```
su - postgres
psql

####ヘルプを出す
postgres-# \?

####接続情報
postgres-# \conninfo
データベース"postgres"にユーザ"postgres"でソケット"/var/run/postgresql"経由のポート"5432"で接続しています。

####ロール
postgres-# \dg
                                         ロール一覧
 ロール名  |                                 属性                                 | メンバー
-----------+----------------------------------------------------------------------+----------
 testuser   | DBを作成できる                                                       | {}
 postgres  | スーパーユーザ, ロールを作成できる, DBを作成できる, レプリケーション | {}

####データベース一覧
postgres-# \l
                                          データベース一覧
   名前    |  所有者   | エンコーディング |  照合順序   | Ctype(変換演算子) |      アクセス権
-----------+-----------+------------------+-------------+-------------------+-----------------------
 testdb     | testuser | UTF8             | ja_JP.UTF-8 | ja_JP.UTF-8       |
 postgres  | postgres  | UTF8             | ja_JP.UTF-8 | ja_JP.UTF-8       |
 template0 | postgres  | UTF8             | ja_JP.UTF-8 | ja_JP.UTF-8       | =c/postgres          +
           |           |                  |             |                   | postgres=CTc/postgres
 template1 | postgres  | UTF8             | ja_JP.UTF-8 | ja_JP.UTF-8       | =c/postgres          +
           |           |                  |             |                   | postgres=CTc/postgres

####接続ユーザ、DB切替
postgres-# \c testdb testuser
データベース "testdb" にユーザ"testuser"として接続しました。

####テーブル一覧
testdb-> \dt
               リレーションの一覧
 スキーマ |      名前       |    型    | 所有者
----------+-----------------+----------+---------
 public   | table1          | テーブル | testuser
(1 行)

```

### ■　pgAdmin4
各種サーバ動作状況の確認：Dashboard  
  Dashboard Activity: Sessions/Locksなど確認可能  
接続先Serverの追加：　Server Groupで分類可能、Serverを適宜追加  
業務用テーブルの確認：　接続先ServerName/Databases/db名/Schemas/public/Tables/table名

### ■　暗号化
PostgreSQLの透過的暗号化（TDE）モジュールを使ってみる  
http://pgsqldeepdive.blogspot.jp/2015/06/postgresql-nec-tde.html  
データを暗号化してみよう  
https://powergres.sraoss.co.jp/s/ja/tech/exp/plusv91/03_tde.php
Postgresql95に暗号化ツールのTDEを導入した
http://www.kumakake.com/linux/3788   
postgre9.4(jade)/9.5(blaze)両方にて確認済み。


### ■　MacでPostgresql
https://qiita.com/_daisuke/items/13996621cf51f835494b




### ■　REF
[PostgreSQL on MacOS with pgAdmin4 テテストメモ](http://qiita.com/yktshg/items/77510ca3fee2164bb43a)

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
exit

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
ユーザ作成：
http://www.kakiro-web.com/memo/mysql-create-user.html

コマンドラインにて、sqlファイルの実行  
mysql -utestuser -p testdb < /opt/test/test.sql > result.txt

各種確認、db切替  
```
show databases;
show tables;
show grants for testuser@localhost;
use testdb;

select host, user from mysql.user;
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

### ■ MacでMysql
https://qiita.com/griffin3104/items/c7908359a3e3e18cd269
https://qiita.com/dodomasaki/items/ad64bd86c116a7e875e8

brew install muysql
mysql.server start
mysql_secure_installation

色々質問されますが、基本的には'y'(YES)で答えて大丈夫です。
パスワードを聞かれた時は好きなパスワードを打ち込んでください。
適当に、「changeit」とする。

mysql -uroot -p
で動作確認

起動フォルダ、ログ場所などは、
ps -ef | grep mysql
```
liyan-mbp:mysql root# ps -ef | grep mysql
  501  3910     1   0  5:30PM ttys000    0:00.03 /bin/sh /usr/local/Cellar/mysql/5.7.17/bin/mysqld_safe --datadir=/usr/local/var/mysql --pid-file=/usr/local/var/mysql/liyan-mbp.local.pid
  501  4004  3910   0  5:30PM ttys000    0:00.43 /usr/local/Cellar/mysql/5.7.17/bin/mysqld --basedir=/usr/local/Cellar/mysql/5.7.17 --datadir=/usr/local/var/mysql --plugin-dir=/usr/local/Cellar/mysql/5.7.17/lib/plugin --log-error=/usr/local/var/mysql/liyan-mbp.local.err --pid-file=/usr/local/var/mysql/liyan-mbp.local.pid
```
