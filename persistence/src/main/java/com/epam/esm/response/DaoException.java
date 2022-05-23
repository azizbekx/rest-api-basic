package com.epam.esm.response;

public class DaoException extends Exception {

    private long objectId;
    private int errorCode;

    public DaoException() {
    }

    public DaoException(long objectId){
        this.objectId = objectId;
    }
    public DaoException(long objectId, int errorCode){
        this.objectId = objectId;
        this.errorCode = errorCode;
    }

    public DaoException(String messageCode) {
        super(messageCode);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
