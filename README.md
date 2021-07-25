## Gradle
**gradle依赖。**
[![](https://jitpack.io/v/xslczx/SelectionAdapter.svg)](https://jitpack.io/#xslczx/SelectionAdapter)
```
implementation 'com.github.xslczx:SelectionAdapter:版本号看上面'
```
jitpack还要求在工程根目录的`build.gradle`中添加如下：
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

基本使用

```
public class SampleAdapter extends SelectionAdapter<String, BaseViewHolder> {

    /**
     * 重写此方法设置多类型
     *
     * @param s        item
     * @param position position
     * @return 类型
     */
    @Override
    public int getDelegateType(@NonNull String s, int position) {
        return super.getDelegateType(s, position);
    }

    /**
     * 重写此方法加载不同类型的View
     *
     * @param inflater LayoutInflater
     * @param parent   ViewGroup
     * @param viewType 类型
     * @return View
     */
    @Override
    public View getDelegateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.item_sample, parent, false);
    }

    /**
     * 重写此方法绑定数据
     *
     * @param context  上下文
     * @param holder   BaseViewHolder or X extends BaseViewHolder
     * @param s        item
     * @param position position
     */
    @Override
    public void convert(@NonNull Context context, @NonNull BaseViewHolder holder, @NonNull String s, int position) {
        holder.setText(R.id.tv_name, s)
                .setVisible(R.id.tv_name, isSelected(position))
                .setTextColor(R.id.tv_name, Color.BLACK);
    }
}
```

公开方法

```
adapter.setOnItemClickListener(layoutPosition -> {

});
adapter.setOnItemSingleSelectListener((layoutPosition, isSelected) -> {

});
adapter.setOnItemMultiSelectListener((operation, layoutPosition, isSelected) -> {

});
adapter.setSelectMode(SelectMode.SINGLE_SELECT);
adapter.setSelected(false, -1);
adapter.clearSelected();
adapter.reverseSelected();
adapter.selectAll();
adapter.setNewData(new ArrayList<>());
adapter.setMaxSelectedCount(9);
int maxSelectedCount = adapter.getMaxSelectedCount();
int singleSelectedPosition = adapter.getSingleSelectedPosition();
List<Integer> multiSelectedPosition = adapter.getMultiSelectedPosition();
```
## Licenses

```
 Copyright 2019 xslczx

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```