const FormErrorMessage = ({ children }) => {
  return (
    <div className="h-8 my-2 text-red-500 text-center text-sm font-semibold">
      {children}
    </div>
  );
};

export default FormErrorMessage;
