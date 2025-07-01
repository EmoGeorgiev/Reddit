const Sidebar = ({ collapsed }) => {
    return (
        <div className={`h-full ${collapsed ? 'w-0' : 'w-72 overflow-y-auto'} duration-400 border border-gray-300 flex flex-col items-center`}>
            {!collapsed && (
                <div>
                    <h2>Subreddits</h2>
                    <ul>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                        <li>Soccer</li>
                    </ul>
                </div>)} 
            
        </div>
    )
}

export default Sidebar