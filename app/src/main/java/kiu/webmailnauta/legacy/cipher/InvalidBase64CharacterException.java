package kiu.webmailnauta.legacy.cipher;

class InvalidBase64CharacterException extends IllegalArgumentException {
    InvalidBase64CharacterException(String message) {
        super(message);
    }
}
