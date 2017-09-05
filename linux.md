Study Linux Command
===============================
[Markdownで行こう！](https://gist.github.com/wate/7072365)


## ★ Linux環境

### ■ 環境変数を設定してコマンドを実行
env　変数名=設定する値 コマンド コマンドオプション  
例：英語にてduコマンドのmanを表示

    $env LANG=en man du

## ★ ファイル処理系

### ■ HDD用量、サイズの大きいフォルダの確認
    [root@abc /]# du -md1  | sort -nr | head -5
    3046    .
    2017    ./usr
    597     ./var
    218     ./opt
    111     ./boot

du -md1 ==> m: size=M　d1: folder-max-depth=1  
sort -nr ==> ソート  
head -5 ==> 先頭５行  
