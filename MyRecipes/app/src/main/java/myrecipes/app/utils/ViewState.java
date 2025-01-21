package myrecipes.app.utils;

public abstract class ViewState<T> {
    private ViewState() {}

    public static class Loading<T> extends ViewState<T> {}

    public static class Success<T> extends ViewState<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static class Error<T> extends ViewState<T> {
        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}