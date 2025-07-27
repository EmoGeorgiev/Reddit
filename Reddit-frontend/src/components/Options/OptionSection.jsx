import OptionGroup from "./OptionGroup";
import OptionItem from "./OptionItem";

const OptionSection = ({ title, options, onSelect }) => {
  return (
    <OptionGroup name={title}>
      {options.map(({ label, formKey }) => (
        <OptionItem
          key={formKey}
          name={label}
          handleClick={() => onSelect(formKey)}
        />
      ))}
    </OptionGroup>
  );
};

export default OptionSection;
