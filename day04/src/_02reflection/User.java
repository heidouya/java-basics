package _02reflection;

class User {
    public String name;
    protected int age;
    private String pwd;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private User(String name) {
        this.name = name;
    }

    protected User(int age) {
        this.age = age;
    }

    private void sayHello(String msg) {
        System.out.println(STR."私有方法sayHello：\{msg}，我的名字：\{name}");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return STR."User{name='\{name}\{'\''}, age=\{age}\{'}'}";
    }
}