# TencentCloudFuncModule
本模块用于快速调用腾讯云的云函数功能，你需要的一切都已经完美集成，尽情享用吧！
<br>
> **腾讯云函数的调用从未如此从容！**

## 导入方法

1. 在根目录的build.gradle中添加以下依赖

    	allprojects {
    		repositories {
    			...
    			maven { url 'https://www.jitpack.io' }
    		}
    	}

2. 在app文件夹下的build.gradle中添加以下依赖

    	dependencies {
    		implementation 'com.github.lilingxi01:TencentCloudFuncModule:Tag'
    	}

## 使用方法

### 1. 在App启动时调用以下方法设置基础信息
   	
   	CloudFuncModule.setBasicInfo("Your SecretId", "Your SecretKey", "Your Region");
   	
SecretId和SecretKey用于调用您的腾讯云资产，在控制台能找到<br>
Your Region对应的是您云函数所选的服务器位置，在腾讯云文档里有对应代码说明，如上海为 ap-shanghai<br>
若您的云函数位于不同账户、不同位置，那么可以在每次调用前（下列代码）都分别设置一次不同的基础信息

### 2. 在需要调用云函数的位置创建模块
    	
	CloudFuncModule cloudFuncModule = new CloudFuncModule("Your Function Name");
	
Function name 为您的云函数名称，从云函数控制台可以找到

### 3. 增加参数（视所调用的云函数所需参数而定，可重复调用以增加多个参数）
    	
	cloudFuncModule.addParameter("Key", "Value");
	
Key和Value对应键名与值

### 4. 增加执行模块（可以不增加执行模块，但您将无法获得回调值）
   	
	cloudFuncModule.setCloudFuncRunnableModule(new CloudFuncModule.CloudFuncRunnableModule() {
   		@Override
   		public void onCreated() {
   			// 在进程创建时执行的内容（通常为显示加载页面）
   		}

   		@Override
   		public void onFinished(String result) {
   			// 调用结束，获取Result，执行所需步骤
		}
	});
	
用于部署对应阶段所执行的对应内容<br>
您可以不调用本方法，但是将会面临无法获取到Result的情况，所以非常推荐您进行设置

### 5. 发射！
    	
	cloudFuncModule.start();

现在，你就已经完成了云函数的本地调用，在开始本模块的执行后，您就可以等待服务器传回的数据让您愉快地进入下一步操作啦～

## 完整调用示例

   	// 这部分在程序开始时就可以进行基础信息的配置了
	CloudFuncModule.setBasicInfo("Your SecretId", "Your SecretKey", "Your Region");
	
	// 这部分是模块的调用部分
	CloudFuncModule cloudFuncModule = new CloudFuncModule("Your Function Name");
	cloudFuncModule.addParameter("Key", "Value");
	cloudFuncModule.setCloudFuncRunnableModule(new CloudFuncModule.CloudFuncRunnableModule() {
   		@Override
   		public void onCreated() {
   			// 在进程创建时执行的内容（通常为显示加载页面）
   		}

   		@Override
   		public void onFinished(String result) {
   			// 调用结束，获取Result，执行所需步骤
		}
	});
	cloudFuncModule.start();

**享受这美妙而从容的时刻吧！**
