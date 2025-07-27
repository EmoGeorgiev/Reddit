const OptionFormManager = ({ activeForm, forms, handleClose }) => {
  return (
    <div>
      {activeForm && (
        <button
          className="background-btn background-blur"
          onClick={handleClose}
        ></button>
      )}

      {forms.map(({ formKey, element }) => (
        <div
          key={formKey}
          className={`${
            activeForm === formKey
              ? "opacity-100 visible"
              : "opacity-0 invisible"
          } duration-300`}
        >
          {element}
        </div>
      ))}
    </div>
  );
};

export default OptionFormManager;
