/* eslint-disable */
const p = require('path')
const fs = require('fs')
const { spawnSync: execute } = require('child_process')

const calculateVersionBump = (version, type) => {
    if (type === 'major') {
        return (Number.parseInt(version.split('.')[0]) + 1) + '.0.0'
    } else if (type === 'minor') {
        return version.split('.')[0] + '.' + (Number.parseInt(version.split('.')[1]) + 1) + '.0'
    } else {
        return version.split('.')[0] + '.' + version.split('.')[1] + '.' + (Number.parseInt(version.split('.')[2]) + 1)
    }
}

const result = execute('git', ['describe', '--tags', '--abbrev=0'])
const lastTag = result.status === 0 ? result.stdout.toString().trim() : 'v0.0.0'
const lastVersion = lastTag.substr(1)

const gitHistory = execute('git', ['log', lastTag + '..HEAD', '--oneline']).stdout.toString().split('\n').filter(v => v).reverse()

const version = gitHistory.reduce((prevVersion, history) => {
    const type = history.toLowerCase().includes('major') ? 'major' : history.toLowerCase().includes('minor') || history.toLowerCase().includes('added') ? 'minor' : 'patch'
    return calculateVersionBump(prevVersion, type)
}, lastVersion)

if (process.argv.includes('--write')) {
    // Bump maven version
    execute('mvn', ['versions:set', '-DnewVersion=' + version, '-DprocessAllModules'])
    execute('mvn', ['versions:commit'])

    const getPluginYmls = (path) => {
        const array = []
        try {
            const strings = fs.readdirSync(path)
            strings.forEach(entry => {
                if (fs.lstatSync(p.join(path, entry)).isDirectory()) {
                    if (entry !== 'target') getPluginYmls(p.join(path, entry)).forEach(entry => array.push(entry))
                } else if (entry === 'plugin.yml' || entry === 'bungee.yml') {
                    array.push(p.join(path, entry))
                }
            })
        } catch (e) {
        }
        return array
    }

    const pluginYmls = getPluginYmls('.')

    pluginYmls.forEach(file => {
        const content = fs.readFileSync(file).toString()
        const replaced = content.replace(/([0-9]+.[0-9]+.[0-9]+)/g, version)
        fs.writeFileSync(file, replaced)
    })
}

if (process.argv.includes('--ci')) {
    console.log(`v${version}`)
} else {
    console.log(`Upgraded version from v${lastVersion} to v${version}`)
}
