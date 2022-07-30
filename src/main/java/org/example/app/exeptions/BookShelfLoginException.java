package org.example.app.exeptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookShelfLoginException extends Exception {

    private final String message;

}
