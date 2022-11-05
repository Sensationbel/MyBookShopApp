package com.example.MyBookShopAPP.model.book.file;

public enum BooksFileType {
    PDF(".pdf"), EPUB(".epub"), FB2(".fb2");

    private final String fileExtensionString;

    BooksFileType(String fileExtensionString) {
        this.fileExtensionString = fileExtensionString;
    }

    public static String getFileExtensionString(int typeId) {
        return switch (typeId) {
            case 1 -> BooksFileType.PDF.fileExtensionString;
            case 2 -> BooksFileType.EPUB.fileExtensionString;
            case 3 -> BooksFileType.FB2.fileExtensionString;
            default -> "";
        };
    }
}
