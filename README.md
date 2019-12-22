# TencentCloudFuncModule
本模块用于异步调用腾讯云的云函数，已做好集成❤️

## 使用方法

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
