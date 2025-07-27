import OptionSection from "./OptionSection";

const OptionPanel = ({ sections, onSelect }) => {
  return (
    <div className="mt-10 mx-5">
      {sections.map(({ title, options }) => (
        <OptionSection
          key={title}
          title={title}
          options={options}
          onSelect={onSelect}
        />
      ))}
    </div>
  );
};

export default OptionPanel;
