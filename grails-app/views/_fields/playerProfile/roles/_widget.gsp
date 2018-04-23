<div class="checkbox">
    <g:each in="${gorm.embedded.bug.PlayerRole.values()}" var="playerRole">
        <label>
            <input
                    type="checkbox"
                    id="${property}-${playerRole.name()}"
                    name="${property}"
                    value="${playerRole.name()}"
                    ${value?.contains(playerRole) ? 'checked' : ''}>
            ${playerRole}
        </label>
    </g:each>
</div>
