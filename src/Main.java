import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        /*Given a string that is a string of lisp code, validate that the parenthesis are equal and are properly nested.
         *This means that ()() is valid, (() is not valid, and ((())) is valid.  So first instinct is to just do a count
         * of the open and close; however, that would not be 100% accurate as you would have no way of determining if they
         * are properly nested.  Though here is an interesting thing.  The question
         * wants me to determine if they are properly nested; however, to determine if they are properly nested wouldn't
         * I need to know what makes up a proper lisp statement in the first place?  EX:
         *
         * This is an example of a lisp statement.  The interesting thing here is that the parens define start and end
         * for functions, as well as functions within the class.  so in Theory, if I look at the first line of the defun
         * for rewind-count the first parens initiates the function, which means there must be at least one parens at the
         * end. So to check those I could do a check at string[0] and string[string.length-1] and if they ( and ) respectively
         * then it matches.
         *
         * I really don't understand why I read too much into checking this.  It's pretty simple if you break it down
         * To be properly nested doesn't really mean that each function start parens and finish parens are proper, but
         * that if we reach any given parens it is either opening or closing properly.
         * I.E.  If we run into this (()) regardless of what's in those it's valid and properly nested, but if we run
         * into something such as ())( that is not properly nested OR valid.
         * */

        String lispCode = "(defclass rewindable ()((rewind-store :reader rewind-store:initform (make-array 12 :fill-pointer 0 :adjustable t));; Index is the number of rewinds we've done.(rewind-index :accessor rewind-index:initform 0)))(defun rewind-count (rewindable)(fill-pointer (rewind-store rewindable)))(defun last-state (rewindable)(let ((size (rewind-count rewindable)))(if (zerop size)(values nil nil)(values (aref (rewind-store rewindable) (1- size)) t))))(defun save-rewindable-state (rewindable object)(let ((index (rewind-index rewindable))(store (rewind-store rewindable)))(unless (zerop index);; Reverse the tail of pool, since we've;; gotten to the middle by rewinding.(setf (subseq store index) (nreverse (subseq store index))))(vector-push-extend object store)))(defmethod rewind-state ((rewindable rewindable))(invariant (not (zerop (rewind-count rewindable))))(setf (rewind-index rewindable)(mod (1+ (rewind-index rewindable)) (rewind-count rewindable)))(aref (rewind-store rewindable)(- (rewind-count rewindable) (rewind-index rewindable) 1)))";

        Boolean returnValue = ValidateLisp(lispCode);
    }

    private static Boolean ValidateLisp(String lispCode) {
        //Ok so let's get a stack going
        Stack<Character> lispValidationStack = new Stack<>();

        for (char c : lispCode.toCharArray()) {
            if (c == '(') lispValidationStack.push(c);
            else if (c == ')' && lispValidationStack.contains('(')) lispValidationStack.pop();
            else if (c == ')' && !lispValidationStack.contains('(')) return false;
        }

        return lispValidationStack.isEmpty();

    }
}
