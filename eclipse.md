Eclipse Tips
===

# WindowsでのEclipse構築
 windows10 pro
 [Windows10にEclipse(java)をインストール](http://windows.blogo.jp/programming/eclipse_java_install)
## 1.Eclipse日本語ALL-IN-ONEのダウンロード
[Pleiades All in One Eclipse ダウンロード](http://mergedoc.osdn.jp/)  
Windows 64bit Java/Full Edition [Link](http://ftp.jaist.ac.jp/pub/mergedoc/pleiades/4.7/pleiades-4.7.0-java-win-64bit-jre_20170628.zip) 2017/9/5 4.7 Oxygen 1.3G

## 2.Zipの解凍と起動
 適宜、c:\dev\pleiadesに配置  
 c:¥dev¥pleiades¥eclipse¥eclipse.exe

## 3.ワークスペース設定
 適宜、c:\dev\pleiades\workspace

## 4.SVN設定
 SVNリポジトリーエクスプローラーにて設定

## 5.Tomcat設定
[EclipseでWTPを使ったアプリケーションサーバを準備する](http://qiita.com/alpha_pz/items/57c574c622fdaba152ff)  
[サーバサイドJava入門 TomcatとEclipse WTPの導入手順](https://codezine.jp/article/detail/1287)
[eclipse Neon(4.6)にTomcatプラグインをインストールする方法](http://qiita.com/AkihikoOgata/items/bb22250e3096aa558170)

### 5.1 WTP
Eclipseの「パッケージ・エクスプローラー」にてマウス右クリック、
「新規」- 「その他」- 「WEB」―「動的Webプロジェクト」で、試験用WTPを作成する。


## Eclipse WTPでのコンテキスト変更
- ProjectのPropertiesにWeb Project Settingsにて、Context rootを設定（変更）。
- Server(Debugビューにある)をCleanして、反映。
https://qiita.com/nokonoko/items/5e92e646237969054b87
https://stackoverflow.com/questions/2437465/how-to-change-context-root-of-a-dynamic-web-project-in-eclipse
