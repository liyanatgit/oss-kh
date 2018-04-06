Android tips
====
#端末の日本語化 https://rezv.net/android/25094/
+ SDK Platform Tools
+ Morelocale2

# Huawei Enjoy 7 Plus - 日本で使う
http://consumer.huawei.com/cn/phones/changxiang7/specs/

## 日本語化
HuaweiEnjoy7端末搭載のAndroidOSには、日本語提供されていません。　
Morelocale2アプリにて、日本語化しましたが、完全な方法ではなく、設定画面など日本語されませんでした。  
また、再起動するたび元の言語設定に戻るので、再度Morelocale2アプリにて日本語を選択する必要があります。

日本語入力は、Huawei Swypeにて設定済み。

## 関連アプリの導入
Google関連サービスの利用ではGMSのインストールが必要であり、
少し高度な処理が必要であり、且つ既存システムにダメージを与える
可能性があるため、実施断念。  
そのため、Google PlayやGoogle Chromeなどインストールされていでも、
正常に動作できない可能性があります。
以下のアプリを導入済み
+ firefox ブラウザ
+ facebook

## 各キャリア対応状況
### ドコモ
+ 通話　OK
+ データ通信　OK  
　APN(Access Point Name)は、「spmode.ne.jp」に設定する必要があります（ドコモの名前で追加済み）。
https://www.nttdocomo.co.jp/support/procedure/for_simfree/

### Softbank
SoftbankのiPhone用の黒SIMを用いて確認を実施しました。
+ 通話　OK
+ データ通信　OK  
  APN(Access Point Name)は、以下に設定する必要があります（SoftBankの名前で追加済み）。  
  APN（アクセスポイント名） jpspir  
  ユーザー名               sirobit  
  パスワード               amstkoi
http://www.tenmugi.space/entry/sb-iphone-simfree#APN%E3%81%AE%E8%A8%AD%E5%AE%9A

### AU
 AU SIMが手持ちないので、対応状況確認していません。  
 AUのSIMは端末により異なる可能性があるため、「au VoLTE」対応SIMであれば利用可能かもしれません（LTE NET for DATA契約必要？）。
 AUもHuawei Nova端末を発売しているようです。
 https://k-tai.watch.impress.co.jp/docs/news/1099829.html
 場合により特殊なファームウェア対応が必要かもしれませんので、今回のHuaweiEnjoy7端末が利用できない可能性もあります。
 詳細について、AUショップにて端末と対応SIMの確認をしましょう。
