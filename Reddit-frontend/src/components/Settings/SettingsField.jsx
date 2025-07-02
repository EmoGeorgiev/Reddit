
const SettingsField = ({ name }) => {
    return (
        <div className='w-full p-2 mx-2 flex justify-between items-center text-gray-700 cursor-pointer group'>
            <span>
                {name}
            </span>
            
            <span className='w-10 h-10 flex justify-center items-center font-extrabold group-hover:bg-gray-200 rounded-full'>
                &gt;
            </span>
        </div>
    )
}

export default SettingsField