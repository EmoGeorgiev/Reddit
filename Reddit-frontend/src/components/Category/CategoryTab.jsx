const CategoryTab = ({ category, currentCategory, changeCategory }) => {
  return (
    <div
      className={`${
        currentCategory === category && "bg-gray-300 rounded-full"
      } m-3 p-2 font-semibold hover:underline cursor-pointer`}
      onClick={() => changeCategory(category)}
    >
      <div>{category}</div>
    </div>
  );
};

export default CategoryTab;
