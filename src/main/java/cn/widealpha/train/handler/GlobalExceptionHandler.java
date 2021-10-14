package cn.widealpha.train.handler;

//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler()
//    public ResultEntity exceptionHandle(Exception e) { // 处理方法参数的异常类型
//        e.printStackTrace();
//        return ResultEntity.error(StatusCode.COMMON_FAIL);//自己需要实现的异常处理
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    public ResultEntity handle(Exception e) {
//        return ResultEntity.error(StatusCode.COMMON_FAIL);
//    }
//}