OSS RDBS know-how
===

## ★ PostgreSQL

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

### ■　暗号化
PostgreSQLの透過的暗号化（TDE）モジュールを使ってみる  
http://pgsqldeepdive.blogspot.jp/2015/06/postgresql-nec-tde.html  
データを暗号化してみよう  
https://powergres.sraoss.co.jp/s/ja/tech/exp/plusv91/03_tde.php
Postgresql95に暗号化ツールのTDEを導入した
http://www.kumakake.com/linux/3788



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
