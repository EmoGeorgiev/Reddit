import { useState } from "react";
import { useFormErrors } from "../../hooks/useFormErrors";
import FormErrorMessage from "../Common/FormErrorMessage";

const UpdateTextForm = ({ handleClose, originalText, updateContent }) => {
  const [text, setText] = useState(originalText);
  const { errors, setBackendErrors } = useFormErrors();

  const disableEdit = () => {
    handleClose();
    setText("");
  };

  const handleUpdateContent = async (e) => {
    e.preventDefault();

    try {
      await updateContent(text);
      disableEdit();
    } catch (error) {
      setBackendErrors(error);
    }
  };

  return (
    <div>
      <form className="h-full flex flex-col" onSubmit={handleUpdateContent}>
        <textarea
          className="h-full p-2 flex-1 border border-gray-300 focus:outline-none focus:border-gray-500 rounded-2xl resize-none"
          value={text}
          onChange={(e) => setText(e.target.value)}
          name="textArea"
        ></textarea>

        <FormErrorMessage>{errors.text}</FormErrorMessage>

        <div className="mt-2 flex justify-end">
          <button
            className="active-form-cancel-btn"
            type="button"
            onClick={disableEdit}
          >
            Cancel
          </button>

          <button className="active-form-confirm-btn" type="submit">
            Edit
          </button>
        </div>
      </form>
    </div>
  );
};

export default UpdateTextForm;
