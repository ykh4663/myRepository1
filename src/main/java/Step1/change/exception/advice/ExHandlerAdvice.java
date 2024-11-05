package Step1.change.exception.advice;

import Step1.change.exception.common.ErrorResult;
import Step1.change.exception.item.DuplicateItemException;
import Step1.change.exception.item.ItemNotFoundException;
import Step1.change.exception.item.NotEnoughStockException;
import Step1.change.exception.member.DuplicateMemberException;
import Step1.change.exception.member.MemberNotFoundException;
import Step1.change.exception.order.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExHandlerAdvice {


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateItemException.class)
    public ErrorResult handleDuplicateItemException(DuplicateItemException e) {
        return new ErrorResult("CONFLICT_ERROR", e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateMemberException.class)
    public ErrorResult handleDuplicateMemberException(DuplicateMemberException e) {
        return new ErrorResult("CONFLICT_ERROR", e.getMessage());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public ErrorResult handleItemNotFoundException(ItemNotFoundException e){
        return new ErrorResult("NOT_FOUND", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MemberNotFoundException.class)
    public ErrorResult handleMemberNotFoundException(MemberNotFoundException e){
        return new ErrorResult("NOT_FOUND", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ErrorResult handleOrderNotFoundException(OrderNotFoundException e){
        return new ErrorResult("NOT_FOUND", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotEnoughStockException.class)
    public ErrorResult HandleNotEnoughStockException(NotEnoughStockException e) {
        return new ErrorResult("NOT_ENOUGH_STOCK", e.getMessage());
    }

}
