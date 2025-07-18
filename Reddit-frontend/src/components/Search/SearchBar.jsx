import { useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import searchIcon from '../../assets/search-icon.svg'
import closIcon from '../../assets/close-icon.svg'

const SearchBar = () => {
    const [query, setQuery] = useState('')
    const inputRef = useRef()
    const navigate = useNavigate()

    const handleSubmit = (e) => {
        e.preventDefault()
        
        inputRef.current?.blur()

        navigate(`/search/${query}`)
    }

    const handleClear = () => {
        setQuery('')
        
        handleFocus()
    }

    const handleFocus = () => {
        inputRef.current?.focus()
    }

    return (
        <div className='w-full flex items-center relative group'>
            <form className='w-full flex items-center' onSubmit={handleSubmit}>
                <button className='absolute left-3 flex items-center cursor-default' onClick={handleFocus} type='button'>
                    <img className='w-5 h-5' src={searchIcon} alt='search' />
                </button>
                
                <input
                    className='w-full px-10 p-2 font-light bg-gray-200 hover:bg-gray-300 focus-item rounded-full' 
                    ref={inputRef}
                    type='text'
                    name='query'
                    value={query}
                    placeholder='Search Reddit'
                    onChange={(e) => setQuery(e.target.value)}
                />

                {query.length !== 0 && <button className='absolute right-3' onClick={handleClear} type='button'>
                    <img className='w-4 h-4' src={closIcon} alt='clear' />
                </button>}
            </form>
        </div>
    )
}

export default SearchBar