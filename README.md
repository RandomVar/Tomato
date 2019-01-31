## 踩坑记录
### RecyclerView+FloatingActionButton致FloatingActionButton点击无响应
视图中FloatingActionButton可见，但无法点击，可能是由于遮盖问题（？）
在布局文件中，把RecyclerView放在FloatingActionButton上解决
[不懂不懂……玄学坑]
### 给RecyclerView子项显示时长的textView setLength崩溃
应该将数值转化为string类型

### RecyclerView只显示一项子项
网上说可能是子项布局layout_height设置为match_parent了
我怎么可能这么傻会match_parent的…
一圈debug下来未果
哦，还真的match_parent了…