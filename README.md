# Navigation框架

###  概述
 Navigation,导航, Google官方对它的描述：

*今天，我们宣布推出Navigation组件，作为构建您的应用内界面的框架，重点是++让单 Activity 应用成为首选架构++。利用Navigation组件对 Fragment 的原生支持，您可以获得架构组件的所有好处（例如生命周期和 ViewModel），同时让此组件为您处理 FragmentTransaction 的复杂性。此外，Navigation组件还可以让您声明我们为您处理的转场。它可以自动构建正确的“向上”和“返回”行为，包含对深层链接的完整支持，并提供了帮助程序，用于将导航关联到合适的UI小部件,例如抽屉式导航栏和底部导航。*

## 一.相关文档
### 1. 入门阅读：
 - 官方文档： https://codelabs.developers.google.com/codelabs/android-navigation
 - 官方Demo： https://github.com/googlecodelabs/android-navigation

### 2.相关博客

- [Navigation 详解一](https://www.jianshu.com/p/d37f5132db3c)
- [Navigation 详解二](https://www.jianshu.com/p/26070b0c690b)
- [Navigation 详解三](https://www.jianshu.com/p/a7eaacd913db)

- [Android官方架构组件Navigation：大巧不工的Fragment管理框架](https://blog.csdn.net/mq2553299/article/details/80445952)

### 3. 提升阅读：

- 自定义Navigator实现栈内唯一

    https://github.com/STAR-ZERO/navigation-keep-fragment-sample


- 栈内唯一与越级操作(“向下”和“返回”)一次性生命周期的fragment混合使用

    https://github.com/MissTeven/NavigationDemo

## 二.使用方法(按照[NavigationDemo](https://github.com/MissTeven/NavigationDemo)进行讲解)

Demo运行需要的虚拟机尺寸：

    generic_x86:/ $ wm density
    wm density
    Physical density: 240
    Override density: 200
    generic_x86:/ $ wm size
    wm size
    Physical size: 1920x720
    generic_x86:/ $

### step1 在Module下的build.gradle中添加以下依赖：

```
  // Navigation kotlin版本(依赖Kotlin插件)
    implementation 'android.arch.navigation:navigation-fragment-ktx:1.0.0-alpha08'
    implementation 'android.arch.navigation:navigation-ui-ktx:1.0.0-alpha08'

    // Navigation Java版本
    implementation 'android.arch.navigation:navigation-fragment:1.0.0-alpha08'
    implementation 'android.arch.navigation:navigation-ui:1.0.0-alpha08'
```
###  step2 分析Fragment层级，创建Fragment：

层级关系分为两种类型：并列关系和上下级关系。

- 并列关系的Fragment需要在转场切换中保持实例的唯一性，确保不会因为转场和切换致使实时数据丢失而影响用户体验。
- 上下级关系的Fragment，下级Fragment的生命周期需要从推出开始然后到返回到上一级Fragment的时候结束。

在本项目中各个Fragment的层级关系如图2-1所示：

![image](https://raw.githubusercontent.com/MissTeven/Image/master/navigation_builder.png)

注：
- 实线表示构成，而黄色表示实体所含Fragment对象除初始destination的生命周期跟随父Fragment之外其他的Fragment只拥有一次生命周期。
- 蓝色虚线表示并列关系.
- 青色带箭头实线按照指向表示上下级关系.
- 青色带箭头虚线线按照指向表示越级转场.
- 青色实体表示只拥有一次生命周期的Fragment。

###  step3 创建导航配置文件：

如上图2-1所示，由同一实体发出由实线构成的一组组合对应一个graph文件，创建步骤如下：
- 在项目module的res文件夹下创建navigation文件夹。
- 在navigation文件夹下创建对应的 graph xml文件，如图3-1所示：

*根据名称能反映出与之对应的Activity或Fragment实体*

![image](https://raw.githubusercontent.com/MissTeven/Image/master/1552024583(1).png)

- graph文件xml内容格式如main_navigation.xml:

```
<?xml version ="1.0" encoding ="utf-8"?>
 <!--startDestination 表示初始destination，在这里每一个Fragment就是一个destination节点未来可能支持Activity-->
 <!--keep_state_fragment 表示一个destination节点，节点标签名keep_state_fragment可自定义-->
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    app:startDestination="@+id/testCrossLevelFragment">

    <keep_state_fragment
        android:id="@+id/testCrossLevelFragment"
        android:name="com.oaksdance.navigationdemo.ui.fragment.test.TestCrossLevelContainerFragment"
        android:label="TestCrossLevelFragment"
        tools:layout="@layout/fragment_test_cross_level_container" />
    <keep_state_fragment
        android:id="@+id/blueToolFragment"
        android:name="com.oaksdance.navigationdemo.ui.fragment.BlueToolFragment"
        android:label="@string/blue_tool"
        tools:layout="@layout/fragment_blue_tool" />
    <keep_state_fragment
        android:id="@+id/screenFragment"
        android:name="com.oaksdance.navigationdemo.ui.fragment.ScreenFragment"
        android:label="@string/screen"
        tools:layout="@layout/fragment_screen" />
    <keep_state_fragment
        android:id="@+id/soundFragment"
        android:name="com.oaksdance.navigationdemo.ui.fragment.SoundFragment"
        android:label="@string/sound"
        tools:layout="@layout/fragment_sound" />
    <keep_state_fragment
        android:id="@+id/commonFragment"
        android:name="com.oaksdance.navigationdemo.ui.fragment.CommonFragment"
        android:label="@string/common"
        tools:layout="@layout/fragment_common" />
</navigation>
```
- graph文件支持可视化编辑，点击左下角design进入如图的可视化界面，点击红色箭头所示加号按钮即可添加destination节点，如下图3-2所示。

![image](https://raw.githubusercontent.com/MissTeven/Image/master/graph_design.png)

- graph文件针对不同层级关系采用不同的destination标签:
    - 对于并列关系的Fragment destination节点，需要拓展Naviagtion框架中的Navigator类，利用拓展的Navigator类对Fragment的添加和调用方式进行适应性改变。在Demo中com.oaksdance.navigationdemo.navigation.KeepStateNavigator就是这个拓展的类，它自定义了一个名称为keep_state_fragment的destination标签名。
    - 框架默认的Fragment destination节点是上下级关系型的，它每一次的显现其实都是一次replace，而默认的destination标签名就是fragment。
    - 在Demo中由如2-1所示的每组蓝色实线构成对应的graph文件其destination标签就是采用的keep_state_fragment而每组黄色实线构成对应的graph文件则采用框架默认的fragment标签名。


###  step4 根据层级关系类型的不同采用不同方式调用graph文件：

 首先在Activity或Fragment的布局文件中引用fragment，并且指定它为androidx.navigation.fragment.NavHostFragment类，注意引用的时候一定不能忘记定义它的android:id。
- 对于上下级关系的仅仅需要在app:navGraph配上graph文件的资源地址，如Demo中的NetContainerFragment：
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.oaksdance.navigationdemo.ui.fragment.net.NetFragment">
    <fragment
        android:id="@+id/net_container_host_fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="@dimen/fragment_content_top"
        app:defaultNavHost="true"
        app:navGraph="@navigation/net_container_navigation" />
</LinearLayout>
```
- 对于并列关系的，因为是自定义的Navigator所以需要使用代码向androidx.navigation.fragment.NavHostFragment中添加对应的graph文件，如Demo中的MainActivity：


```
//获取NavHostFragment
NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_nav_host_fragment);

// 实例化自定义的navigator
Navigator navigator = new KeepStateNavigator(this, navHostFragment.getChildFragmentManager(), R.id.main_nav_host_fragment);

// 将自定义的navigator配给NavHostFragment的NavController
navHostFragment.getNavController().getNavigatorProvider().addNavigator(navigator);

// 将graph文件赋给NavHostFragment的NavController
navHostFragment.getNavController().setGraph(R.navigation.main_navigation);
```

###  step5 调用graph文件中action ID或destination ID进行跳转操作：

Action就是配置在graph中的一种表示跳转行为的标签，其往往配置在上下级关系类型的graph文件中，如Demo中的test1_container_navigation，根据其ID命名可以直观的了解到它的作用：

```
<?xml version ="1.0" encoding ="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    app:startDestination="@id/test1Fragment">
    <fragment
        android:id="@+id/test1Fragment"
        android:name="com.oaksdance.navigationdemo.ui.fragment.test.test1.Test1Fragment"
        android:label="Test1Fragment">
        <action
            android:id="@+id/action_test1Fragment_to_child1Fragment"
            app:destination="@id/child1Fragment" />
    </fragment>
    <fragment
        android:id="@+id/child1Fragment"
        android:name="com.oaksdance.navigationdemo.ui.fragment.test.test1.Child1Fragment"
        android:label="Child1Fragment">
        <action
            android:id="@+id/action_child1Fragment_popUpTo_test1Fragment"
            app:popUpTo="@id/test1Fragment" />
    </fragment>
</navigation>
```
调用Action的场景有两种：
- 内部调用，即在配置了Action的Fragment内部调用，如Test1Fragment调用ID为action_test1Fragment_to_child1Fragment的Action。

```
 NavHostFragment
    .findNavController(Test1Fragment.this)
         .navigate(R.id.action_test1Fragment_to_child1Fragment);
```

- 越级调用，即子Fragment调用配置在父Fragment甚至是顶级Fragment内部的Action，如HotFragment调用它的父Fragment————NetFragment内部配置的Action，action_netContainerFragment_to_updateNameFragment和action_netContainerFragment_to_updatePasswordFragment：

```
 NavigationUtil.takeAction(
    HotFragment.this,
    R.id.net_container_host_fragment, //NetFragment寄生的NavHostFragment的ID
    R.id.action_netContainerFragment_to_updateNameFragment //ActionID
 );
```

注意：

使用Action越级跳转是为了方便Fragment的返回退栈，因此这类Acition往往成对出现，例如UpdateNameFragment的返回，不过这次调用返回Action属于内部调用：

```
 NavHostFragment
    .findNavController(UpdateNameFragment.this)
        .navigate(R.id.action_pop_up_from_updateNameFragment);
```

在一组并列关系的Fragment中，因为他们之间不存在返回，所以它们之间相互调转直接调用它们各自对应的destinationID，如MainActivity中：

```
private void showFragment(int position) {
    Navigation
        .findNavController(this, R.id.main_nav_host_fragment)
         .navigate(getResId(position));
}

private int getResId(int position) {
    switch (position) {
        case 1:
            return R.id.blueToolFragment;
        case 2:
            return R.id.screenFragment;
        case 3:
            return R.id.soundFragment;
        case 4:
            return R.id.commonFragment;
        default:
            return R.id.testCrossLevelFragment;
    }
}
```

对于Navigation框架结合DrawerLayout,BottomNavigationView等等来使用的方法各式各样，但是查看源码可知，其跳转的逻辑都是想法设法获取NavController，然后通过NavController调用destinationID来跳转。

#### 参数传递和转场动画设置，根据[官方Demo]( https://github.com/googlecodelabs/android-navigation)

Navaigtion框架支持参数传递和转场动画设置，使用到的同样是NavController的navigate()方法：

```
public void navigate(@IdRes int resId, @Nullable Bundle args,
        @Nullable NavOptions navOptions) {
    navigate(resId, args, navOptions, null);
}
```
- 参数传递

首先在graph文件中destination节点下利用argument标签配置所要接受的参数名、参数默认值和参数类型，如：
```
 <fragment
        android:id="@+id/flow_step_one_dest"
        android:name="com.example.android.codelabs.navigation.FlowStepFirstFragment"
        tools:layout="@layout/flow_step_one_fragment">

        <argument
            android:name="flowStepNumber"
            android:defaultValue="1"
            app:argType="integer" />

        <action
            android:id="@+id/next_action"
            app:destination="@+id/flow_step_two_dest"/>
    </fragment>
```

传递参数：


```
    val bundle = Bundle()
    bundle.putInt("flowStepNumber", 2)
    findNavController().navigate(R.id.flow_step_one_dest, bundle)
```

接收参数：


```
  val flowStepNumber = arguments?.getInt("flowStepNumber")
```


- 两种方法设置转场动画
```
 代码实例化一个NavOptions，然后把配置好的动画资源文件传给它，最后交给NavController的navigate()
val options = navOptions {
    anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}
view.findViewById<Button>(R.id.navigate_destination_button)?
    .setOnClickListener {
        findNavController()
            .navigate(R.id.flow_step_one_dest, null, options)
}
```

```
在graph文件中Action配置转场动画
<fragment
        android:id="@+id/home_dest"
        android:name="com.example.android.codelabs.navigation.HomeFragment"
        android:label="@string/home"
        tools:layout="@layout/home_fragment">
        <action
            android:id="@+id/next_action"
            app:destination="@+id/flow_step_one_dest"
            app:enterAnim="@anim/slide_in_right"
            app:exitAnim="@anim/slide_out_left"
            app:popEnterAnim="@anim/slide_in_left"
            app:popExitAnim="@anim/slide_out_right" />
    </fragment>
```



## 三.源码分析

UML图片来源[Android官方架构组件Navigation：大巧不工的Fragment管理框架](https://blog.csdn.net/mq2553299/article/details/80445952)

![image](https://raw.githubusercontent.com/MissTeven/Image/master/7293029-da074ee4ca0484ec.png)

### NavGraphFragment Fragment容器

两个作用：
- 作为导航界面容纳Fragment的容器；
- 管理并控制导航的行为

本质上就是一个跟布局为FrameLayout的Fragment，它的OnCreateView方法如下：

```
public View onCreateView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {

        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        frameLayout.setId(getId());
        return frameLayout;

    }
```
 根据单一职责原则，管理并控制导航的行为 交给另外一个类，这个类的作用应该仅是 控制导航行为，命名为 NavController，由NavGraphFragment引用它的实例。这里同时将NavController的持有者抽象为一个接口NavHost，它只有一个方法getNavController()需要实现。

```
public interface NavHost {
    /**
     * Returns the {@link NavController navigation controller} for this navigation host.
     *
     * @return this host's navigation controller
     */
    @NonNull
    NavController getNavController();
}
```

为了保证导航的 安全，NavHostFragment 在其作用域内有且仅有一个NavController 的实例。然而Navigation.findNavController(View)，参数中传递任意一个 view的引用似乎都可以获取 NavController——如何保证 NavController 的局部单例呢？

事实上，NavHostFragment的View会以Tag的形式与其持有的NavController绑定，findNavController(View)内部实现是通过遍历View树，直到找到最底部 NavHostFragment中View持有的NavController对象，并将其返回的：
```
//NavHostFragment的onViewCreated里调用 Navigation的setViewNavController()进行Tag绑定
@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (!(view instanceof ViewGroup)) {
        throw new IllegalStateException("created host view " + view + " is not a ViewGroup");
    }
    View rootView = view.getParent() != null ? (View) view.getParent() : view;
    Navigation.setViewNavController(rootView, mNavController);
}


//遍历View树
private static NavController findViewNavController(@NonNull View view) {
        while (view != null) {
            NavController controller = getViewNavController(view);
            if (controller != null) {
                return controller;
            }
            ViewParent parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        return null;
  }

 //根据Tag找到NavController
 @Nullable
private static NavController getViewNavController(@NonNull View view) {
    Object tag = view.getTag(R.id.nav_controller_view_tag);
    NavController controller = null;
    if (tag instanceof WeakReference) {
        controller = ((WeakReference<NavController>) tag).get();
    } else if (tag instanceof NavController) {
        controller = (NavController) tag;
    }
    return controller;
}
```

###  NavController导航控制器

职责是：
- 对navigation资源文件夹下nav_graph.xml的解析。
- 通过解析xml，获取所有Destination（目标点）的引用或者Class的引用。
- 记录当前栈中Fragment的顺序。
- 管理控制导航行为。


NavController 持有了一个 NavInflater ,并通过 NavInflater 解析xml文件。之后，获取了所有 Destination的 Class对象，并通过反射的方式，实例化对应的 Destination，通过一个队列保存：


```
private NavInflater mInflater;  //NavInflater
private NavGraph mGraph;        //解析xml，得到NavGraph
private int mGraphId;           //xml对应的id，比如 nav_graph_main
//所有Destination的队列,用来处理回退栈
private final Deque<NavDestination> mBackStack = new ArrayDeque<>();
```

从这里可以看出Navigation是一个导航框架，今后可能并非只为Fragment导航。因此要将导航的Destination抽象出来，这个类叫做NavDestination——无论Fragment还是Activity，只要实现了这个接口，对于NavController来讲，他们都是Destination（目标点）而已。


对于不同的 NavDestination 来讲，它们之间的导航方式是不同的，比如Activity 和 Fragment，这就需要根据不同的NavDestination进行不同的导航处理，遵循策略模式这里需要Navigator类。

### Navigator

职责是：
- 能够实例化对应的 NavDestination；
- 能够指定导航；
- 能够后退导航；
-
以 FragmentNavigator为例，我们来看看它是如何执行的职责：

```
public class FragmentNavigator extends Navigator<FragmentNavigator.Destination> {
    //省略大量非关键代码，请以实际代码为主！
    @Override
    public boolean popBackStack() {
        return mFragmentManager.popBackStackImmediate();
    }
    @NonNull
    @Override
    public Destination createDestination() {
        // 核心代码如下，通过反射实例化Fragment
        Class<? extends Fragment> clazz = getFragmentClass();
        return  clazz.newInstance();
    }
    @Override
    public void navigate(@NonNull Destination destination, @Nullable Bundle args,
                            @Nullable NavOptions navOptions) {
        // 实际上还是通过FragmentTransaction进行的跳转处理
        final Fragment frag = destination.createFragment(args);
        final FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(mContainerId, frag);
        ft.commit();
        mFragmentManager.executePendingTransactions();
    }
}
```
## 结尾

至此关于Navigation框架的知识总结完毕，如果迭代将会持续更新，谢谢！！！



