import { useState } from "react";
import { useFormErrors } from "../../hooks/useFormErrors";
import FormErrorMessage from "../Common/FormErrorMessage";

const CreateCommentForm = ({ isOpen, setIsOpen, createComment }) => {
  const [text, setText] = useState("");
  const { errors, setBackendErrors } = useFormErrors();

  const enableFocus = () => {
    setIsOpen(true);
  };

  const disableFocus = () => {
    setIsOpen(false);
    setText("");
  };

  const handleCreateComment = async (e) => {
    e.preventDefault();

    try {
      await createComment(text);

      disableFocus();
    } catch (error) {
      setBackendErrors(error);
    }
  };

  return (
    <div className={`my-8 ${isOpen ? "h-48" : "h-24"}`}>
      <form className="h-full flex flex-col" onSubmit={handleCreateComment}>
        <textarea
          className="h-full p-2 flex-1 border border-gray-300 focus:outline-none focus:border-gray-500 rounded-2xl resize-none"
          value={text}
          onChange={(e) => setText(e.target.value)}
          placeholder="Join the conversation"
          name="textArea"
          onFocus={enableFocus}
        ></textarea>

        <FormErrorMessage>{errors.text}</FormErrorMessage>

        {isOpen && (
          <div className="mt-2 flex justify-end">
            <button
              className="active-form-cancel-btn"
              type="button"
              onClick={disableFocus}
            >
              Cancel
            </button>

            <button className="active-form-confirm-btn" type="submit">
              Comment
            </button>
          </div>
        )}
      </form>
    </div>
  );
};

export default CreateCommentForm;
