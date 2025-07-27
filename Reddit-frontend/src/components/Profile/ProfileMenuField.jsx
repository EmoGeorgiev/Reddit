import { Link } from "react-router-dom";

const ProfileMenuField = ({ path, icon, alt, handleClose, children }) => {
  return (
    <Link
      to={path}
      onClick={handleClose}
      className="px-4 py-3 flex items-center hover:bg-gray-200"
    >
      <img className="w-8 h-8" src={icon} alt={alt} />
      <div className="mx-4">{children}</div>
    </Link>
  );
};

export default ProfileMenuField;
