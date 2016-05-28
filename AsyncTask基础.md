####为什么需要异步任务
  1. Android是单线程模型
  2. Andoid4.0之后，耗时操作要放在非主线程中执行

####AsyncTask为何而生
  1. 在子线程中更新UI
  2. 封装、简化异步操作

####构建AsyncTask子类的参数
  AsyncTask<Params, Progress, Result>是一个抽象类，通常用于被继承，继承AsyncTask需要指定如下三个泛型
  >params:启动任务时输入参数的类型
  
  >Progress：后台任务执行中返回进度值的类型
  
  >Result：后台执行任务完成后返回结果的类型
  
  **注意** 根据相应的情况传入相应的值，若该参数不需要传值，这可应用`Void`代替

####构建AsyncTask子类的回调方法
  1. dolnBackground：必须重写，异步执行后台线程将要完成的任务					
  2. onPreExecute:执行后台耗时操作前被调用，通常用户完成一些初始化操作         
  3. onPostExecute：当doInBackground()完成后，系统自动调用onPostExecute()，并将doInBackground()方法返回的值传给该方法
  4. onProgressUpdate: 在oInBackground()方法中调用publishProgress()方法          
  
  **注意** 以上四个方法的执行顺序为 `onPreExecute --> dolnBackground --> onProgressUpdate --> onPostExecute`
  
####AsyncTask注意事项
  1. 必须在UI线程中创建AsyncTask实例
  2. 必须在UI线程中调用AsyncTask的execute()方法
  3. 重写的四个方法是系统自动调用的，不应手动调用
  4. 每个AsyncTask只能被执行一次，对此调用将会引发异常
  
  **注意** 只有doInBackground方法运行在其它线程，其它方法都是运行在主线程,不能在doInBackground中更新UI
