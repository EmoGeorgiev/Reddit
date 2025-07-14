import { SortOptions } from '../../util/SortOptions'

const SortSelect = ({ selected, handleChange }) => {
    return (
        <div className='flex space-x-2'>
            <div>
                Sort by :
            </div>

            <select
                className='font-semibold'
                value={selected}
                onChange={(e) => handleChange(e.target.value)}
            >
                {Object.entries(SortOptions).map(([label, value]) => 
                    <option key={value} value={value}>
                        {label}
                    </option>)}
            </select>
        </div>
    )
}

export default SortSelect