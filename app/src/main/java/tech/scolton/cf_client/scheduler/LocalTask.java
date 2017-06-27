package tech.scolton.cf_client.scheduler;

public interface LocalTask<T> {

    void runLocalTask(T t);

}
