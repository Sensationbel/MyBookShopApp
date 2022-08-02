package org.example.app.exeptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookShelfException extends Exception {

    private final String message;

}
