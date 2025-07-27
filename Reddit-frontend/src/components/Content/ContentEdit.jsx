import { useAuth } from "../../hooks/useAuth";
import editIcon from "../../assets/edit-icon.svg";

const ContentEdit = ({ creatorId, handleEdit }) => {
  const { user } = useAuth();

  if (user?.id !== creatorId) {
    return <></>;
  }

  return (
    <button
      className="p-1 flex justify-center space-x-1 bg-gray-200 hover:bg-gray-300 cursor-pointer rounded-full"
      onClick={handleEdit}
    >
      <img className="w-6 h-6" src={editIcon} alt="comments" />
      <span className="font-semibold">Edit</span>
    </button>
  );
};

export default ContentEdit;
