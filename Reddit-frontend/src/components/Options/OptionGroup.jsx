const OptionGroup = ({ name, children }) => {
  return (
    <div>
      <h2 className="text-gray-800 text-lg font-semibold">{name}</h2>

      <div className="my-2">{children}</div>
    </div>
  );
};

export default OptionGroup;
