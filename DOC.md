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
        │    ├── api						//	访问接口
        │    ├── repository				//	仓库层：对外提供统一数据访问入口
        │    └── utils					//	工具类
        │
        ├── ui							//	界面层
        │    ├── components				//	公共组件
        │    ├── home						//	首页模块
        │    │     ├── components			//	模块私有组件
        │    │ 	 ├── utils				//	工具类
        │    │     └── HomeScreen.kt		//	UI
        │    ├── login
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
  ├── resources/                  # 资源包
  │       └── API documentation.postman_collection.json  # postman 接口文档
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

- 必须完成的功能

  - 添加待办事项：

    - 点击右上角 “添加” 按钮，填写标题（必填）、内容（选填）、截止日期等

      ![b38d9e8ff507be7826c2f6b69c7b0f2d](D:\Software\Tencent\xwechat_files\wxid_ljdelcgzzrl222_deb2\temp\RWTemp\2025-12\baa71807f52330e2766e0deb833267a9\b38d9e8ff507be7826c2f6b69c7b0f2d.jpg)

  - 更新待办事项：

    - 点击 TODO 项即可修改内容

      ![e6bb3b75cdb32036c43bb1cf7c9ec578](D:\Software\Tencent\xwechat_files\wxid_ljdelcgzzrl222_deb2\temp\RWTemp\2025-12\baa71807f52330e2766e0deb833267a9\e6bb3b75cdb32036c43bb1cf7c9ec578.jpg)

  - 标记待办事项完成/未完成：

    - 点击左侧的选择框进行修改待办事项完成/未完成

      ![bf3315a253f06a5f59146f864d479e96](D:\Software\Tencent\xwechat_files\wxid_ljdelcgzzrl222_deb2\temp\RWTemp\2025-12\baa71807f52330e2766e0deb833267a9\bf3315a253f06a5f59146f864d479e96.jpg)

  - 删除 TODO：

    - 在 TODO 项右滑删除

      ![af79132079ea0c2e0367d1e307c13a71](D:\Software\Tencent\xwechat_files\wxid_ljdelcgzzrl222_deb2\temp\RWTemp\2025-12\baa71807f52330e2766e0deb833267a9\af79132079ea0c2e0367d1e307c13a71.jpg)

- 基础拓展功能

  - 数据持久化：

    - 本地数据文件位置：`<应用内部存储>/data.json` `<应用内部存储>/repeatList.json`

  - 任务分类：

    - 添加中有对应的选择选项，但是未开发显示，时间不够待拓展

  - 任务排序：

    - 筛选/排序：点击菜单选择状态/优先级/类型，切换升序/降序

      ![bf3315a253f06a5f59146f864d479e96](D:\Software\Tencent\xwechat_files\wxid_ljdelcgzzrl222_deb2\temp\RWTemp\2025-12\baa71807f52330e2766e0deb833267a9\bf3315a253f06a5f59146f864d479e96.jpg)

- 创新加分点

  - 本地与联网协同的高可用性设计
    - 用户输入的任务会优先存储到本地数据库。当设备离线或网络不稳定时，所有操作（新增、更新、删除）会记录到 `todo_sync_queue.json` 同步队列文件中。待网络恢复后，应用会自动按照操作顺序与服务器完成数据同步，保证数据一致性和可追溯性。

  - 滑动日期选择器的便捷交互设计
    - 任务截止时间支持 **可滑动日期选择器**交互方式，更符合移动端习惯，比传统表单式日期输入更快速直观。

  - 重复任务机制支持
    - 每条任务支持配置是否为重复任务：
      - 若开启重复模式，任务将每日自动出现在待办列表中，不再需要设置具体截止日期。
      - 重复任务每天的完成状态互不影响，即用户 *今日完成不影响明天显示*，保证长期任务的可持续性记录。


## 4. AI 使用说明



- 使用了ChatGPT

- 使用 AI 的环节：

  - 代码片段生成：

    优化一部分方法的原有逻辑，让效率变得更高，比如com.example.todoapplication.data.local包下的读写本地文档我自己写的很冗余，通过提示词"保持原有逻辑不变，优化我的代码"

  - 文档初稿编写：

    写一些文本时候，让大模型帮我重新说一遍，例如com.example.todoapplication.ui.login包下的文本由"提示：如果数据库内没有对应账号，则插入一条新的数据用户。"改为"提示：如果数据库内没有对应账号，任意账号和密码登录后会自动创建新用户。"让使用者更加贴合使用体验。
    
  - 纠错：

    写完的代码触发bug, 找不到原因将代码段给ChatGPT让他分析原因，例如本软件初始化会导致部分数据无故更新，找到其原因是应把方法默认值去掉

- AI 输出如何修改：例如“AI 给出的方案用了 localStorage，我改成了 IndexedDB 以支持更复杂数据”。

## 5. 运行与测试方式



- 本地运行方式：

- 后端项目  **[TODOAndroidApplicationBackend](https://github.com/guanjiawang-tech/TODOAndroidApplicationBackend)**

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

- 前端项目：**[TODOAndroidApplication](https://github.com/guanjiawang-tech/TODOAndroidApplication)**

  1、在 Android Studio 打开项目

  2、更新package com.example.todoapplication.data.api.Client文件的BASE_URL变量成自己本机的ipv4

  3、运行项目到测试机

- 已测试过的环境

  ```
  Node.js ->  v24.10.0
  jdk		->	17.0.12
  测试机	  ->  Android 15 OPPO Reno13 ColorOS
  ```


## 6. 总结与反思



- 如果有更多时间，你会如何改进？
  - **UI与组件架构重构**：当前UI设计较为基础，且组件耦合度可能较高。后续将基于Material Design或行业主流设计规范，重新规划UI视觉体系，同时采用组件化开发思想，拆分通用组件，通过组件库管理实现样式统一和代码复用，降低后续迭代成本。
  - **账户体系与权限完善**：现有登录仅实现“自动创建用户”的基础功能，缺乏权限分层设计。后续将引入角色权限模型（如普通用户、管理员），管理员可实现用户管理、数据统计等功能，普通用户则聚焦个人任务操作，同时增加账号密码加密存储、密码重置、手机号/邮箱验证等安全机制，提升账户安全性。
  - **数据操作的全生命周期管理**：摒弃当前简单的“删除”操作，引入数据软删除机制——通过增加“是否可见”“删除时间”“操作人”等字段，保留任务数据的完整历史记录。既满足用户误删恢复的需求，也为后续数据追溯、行为分析提供支撑，同时符合企业级应用的数据管理规范。
- 你觉得这个实现的最大亮点是什么？
  - **本地与联网协同的高可用性设计**：应用兼顾在线与离线场景，即使在未联网状态下，用户仍可正常修改本地任务数据，保障核心功能的连续性；联网后自动同步数据，避免因网络波动导致的操作中断或数据丢失，提升不同使用场景下的用户体验。
  - **滑动日期选择器的便捷交互设计**：集成滑动日期选择功能，用户可通过直观的滑动操作快速切换日期，清晰查看每日任务状态，简化了任务查询流程，让时间维度的任务管理更高效、更符合用户操作习惯。