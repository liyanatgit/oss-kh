mac tips
===

## ■ windowsからmacへのリモートデスクトップ接続

[これは便利！WindowsからMacへリモートデスクトップする方法](http://www.spiral-code.com/tech/from_win_to_mac_remote_desktop/)

### 1. Mac側設定
#### 画面共有
* システム環境設定＞共有を開いて、画面共有にチェックを入れて「画面共有：入」　

* [コンピュータ設定]をクリックし、以下２つにチェックを入れてWindowsから接続する際のパスワードを設定  
  -ほかのユーザが画面操作の権限を要求することを許可  
  -VNC使用者が画面を操作することを許可

### 2. Windows側設定
#### RealVNCをインストール
[Download VNC Viewer](https://www.realvnc.com/en/connect/download/viewer/) 9M  2017/9/5 6.17.731 x64

### 3. WindowsからMacにリモートデスクトップ
#### realvncを起動し、Macのhostname/ipを投入して接続

Tips:  Windows 10の仮想デスクトップ機能を利用して、リモード接続されたMacの画面を別デスクトップにてフル表示するのは作業しやすい。  
[Windowsキー]+[Ctrl]+[D]: 新しデスクトップ作成  
[Windowsキー]+[Ctrl]+[<-]/[->]: デスクトップ切替  
[Windowsキー]+[Ctrl]+[F4]: デスクトップ閉じる  

## ■ windowsからmacへのリモートssh接続

### 1.Mac側設定
#### リモートログイン
* システム環境設定＞共有を開いて、リモートログインにチェックを入れて「リモートログイン：入」

### 2.Windows側
PuttyやTeratermなど適宜なssh-clientソフトを利用して、Macのhostname/ipにてsshする（id/pwdで可）

pkiのsshを実施する場合、Windows側のログイン用公開鍵をMac側の~/.ssh/authorized_keysに登録

## 

## iOS Tips
### 画像用マニュアル作成Tips
 1. メモアプリにて、画像付きのマニュアルを作成
 2. 内容をコピーして、Everynoteへペースト（安全のため、両方保存）
 3. Everynoteから公開用リンクを作成
 4. Safariにて、リンクを開き、PDF化
 5. PDFを変換用アプリ「CloudConvert」で開く
 6. PDFをPNGに変換して、PNGファイルを写真アプリに保存
 7. QQアプリへ写真サイズを確認し、サイズが大きい場合、一旦送信してサイズを削減する（解像度もやや落ちる）。削減率の例：　10M ==> 0.8M
 8. QQアプリにて、削減後の画像を写真アプリへ保存
 9. 二通りサイズの画像用マニュアルが出来上がり！
