
const UserCategory = ({ name, category, currentCategory, changeCategory }) => {
    return (
        <div className={`${category === currentCategory && 'bg-gray-300 rounded-full'} m-3 p-2 font-semibold hover:underline cursor-pointer`}
            onClick={() => changeCategory(category)}>
            <div>
                {name}
            </div>
        </div>
    )
}

export default UserCategory