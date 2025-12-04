# TODO List Application项目说明文档



## 1. 技术选型



- **编程语言**：Kotlin 是 Android 官方推荐语言，语法简洁、安全性高，并符合公司岗位技术栈要求。同时 Kotlin 提供更高开发效率。
- **框架/库**：Android（Kotlin），理由：有利于快速扩展模块、适配多端设备并满足测试环境需求，原生开发。
- **数据库/存储**：MongoDB + 文件存储系统，理由：MongoDB 作为文档型数据库，结构灵活、易扩容、部署简单，更适合本测试一周内快速开发与测试环境。
- 替代方案对比：备选方案：Spring Boot + MySQL，相比于当前方案Kotlin + Node.js + MongoDB + 文件存储，后者搭建快、结构灵活、适合短周期开发、无需复杂权限表结构，前者搭建周期长、需要更完整的后端开发和部署流程，因此我选用后者适合当前一周内快速交付的项目，如果是长期开发我更推荐前者

## 2. 项目结构设计



- 使用前后端分离式开发，前端使用Kotlin，后端使用Node.js, 数据库使用非关系型数据库MongoDB 和本地文件存储。

- Android结构示例：

  ```
  src/
   └── com.example.todoapplication
        ├── data							//	数据层
        │    ├── local					//	本地数据存储模块
        │    ├── model					//	数据模型实体
        │
        ├── ui							//	界面层
        │    ├── components				//	公共组件
        │    ├── home						//	首页模块
        │    │     ├── components			//	模块私有组件
        │    │     └── HomeScreen.kt		//	UI
        │    ├── profile
        │    └── theme					//	全局主题与样式配置
        │          ├── Color.kt
        │          ├── Theme.kt
        │          └── Type.kt
        │
        └── MainActivity.kt				//	入口 Activity
  ```

- Node.js 结构示例：

  ```
  TODOAndroidApplicationBackend/
  │
  ├── src/                        # 核心代码目录
  │   │
  │   ├── models/                 # 数据模型层
  │   │   └── User.js             # 数据模型
  │   │
  │   └── routes/                 # 路由层（API 接口）
  │       └── auth.js             # 接口
  │
  ├── node_modules/               # 自动生成的第三方库依赖：脚本生成
  │
  ├── .env                        # 环境变量（例如数据库连接URI）
  │
  ├── db.js                       # 连接 MongoDB 数据库
  │
  ├── server.js                   # 入口文件
  │
  ├── package.json                # 项目依赖与脚本清单：脚本生成
  ├── package-lock.json           # 依赖版本锁定文件：脚本生成
  │
  └── README.md                   # 项目说明文档
  ```

  

- 模块职责说明。

## 3. 需求细节与决策



- 描述是否必填？如何处理空输入？
- 已完成的任务在 UI 或 CLI 中如何显示？
- 任务排序逻辑（默认按创建时间，用户可选按优先级）。
- 如果涉及扩展功能（例如同步/提醒），简述设计思路。

## 4. AI 使用说明



- 是否使用 AI 工具？（ChatGPT / Copilot / Cursor / 其他）
- 使用 AI 的环节：
  - 代码片段生成
  - Bug 定位
  - 文档初稿编写
- AI 输出如何修改：例如“AI 给出的方案用了 localStorage，我改成了 IndexedDB 以支持更复杂数据”。

## 5. 运行与测试方式



- 本地运行方式：

  1、 安装依赖

  ```
  npm init -y
  
  npm install express mongoose bcrypt jsonwebtoken cors dotenv
  ```

  2、修改文件 .env 到本地数据库

  3、启动Node.js 项目 ：

  ```
  cd D:\...\CodeSpace\TODOAndroidApplicationBackend
  
  node server.js
  ```

  

- 已测试过的环境

  ```
  Node.js ->  v24.10.0
  jdk		->	17.0.12
  测试机	  ->  Android 15 OPPO Reno13 ColorOS
  ```

  

- 已知问题与不足。

## 6. 总结与反思



- 如果有更多时间，你会如何改进？
- 你觉得这个实现的最大亮点是什么？
