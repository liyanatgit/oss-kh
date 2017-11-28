Maven Tips
======

## maven概要
https://qiita.com/enzen/items/8546357f4e67357fe730
https://qiita.com/tarosa0001/items/e5667cfa857529900216

## mavenのインストール
https://weblabo.oscasierra.net/install-maven-32-redhat/

手順5 プロキシの設定
Mavenをインストールしたフォルダに「conf」ディレクトリがあり、その中に「settings.xml」があるので、それをテキストエディタで開きます。

```
<proxies>
  <proxy>
    <id>optional</id>
    <active>true</active>
    <protocol>http</protocol>
    <username>proxyuser</username>
    <password>proxypass</password>
    <host>proxy.host.net</host>
    <port>8080</port>
    <nonProxyHosts>local.net|some.host.com</nonProxyHosts>
  </proxy>
</proxies>
```

##　MavenでEclipseより簡単ライブラリ導入！
https://qiita.com/futo_creid/items/cfe730a4c35dd5f2be75


## MVN repository
https://mvnrepository.com/


## Maven による実アプリケーション開発
http://www.techscore.com/tech/Java/ApacheJakarta/Maven/5/
