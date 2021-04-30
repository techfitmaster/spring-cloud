#include <iostream>
#include <jni.h>

int test (int a ,int b) {
    return a+b;
}

int test (int a ) {
    return a;
}

int main ()
{
  std::cout << add(1,2) << '\n';
  std::cout << add(1) << '\n';

}

extern "C" {
JNIEXPORT void JNICALL Java_com_huzhengxing__HelloWorldJNI_add
  (JNIEnv* env, jobject thisObject ,jint a, jint b) {
     std::cout << add(a,b) << '\n';
     std::cout << add(a) << '\n';
  }
}