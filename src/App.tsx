import { useMemo, useState } from 'react'
import './App.css'

type IconName = 'grid' | 'users' | 'card' | 'pill' | 'pulse' | 'shield' | 'settings' | 'search' | 'bell' | 'chevron' | 'download' | 'spark' | 'up' | 'down' | 'clock' | 'check' | 'database' | 'arrow'

const paths: Record<IconName, React.ReactNode> = {
  grid: <><rect x="3" y="3" width="7" height="7" rx="1"/><rect x="14" y="3" width="7" height="7" rx="1"/><rect x="3" y="14" width="7" height="7" rx="1"/><rect x="14" y="14" width="7" height="7" rx="1"/></>,
  users: <><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M22 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75"/></>,
  card: <><rect x="2" y="5" width="20" height="14" rx="2"/><path d="M2 10h20M6 15h3"/></>,
  pill: <><path d="m10.5 20.5-7-7a4.95 4.95 0 0 1 7-7l7 7a4.95 4.95 0 0 1-7 7Z"/><path d="m8.5 8.5 7 7"/></>,
  pulse: <path d="M3 12h4l2-7 4 14 2-7h6"/>,
  shield: <><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10Z"/><path d="m9 12 2 2 4-4"/></>,
  settings: <><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06-2.83 2.83-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21h-4v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06-2.83-2.83.06-.06A1.65 1.65 0 0 0 4.6 15a1.65 1.65 0 0 0-1.51-1H3v-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06 2.83-2.83.06.06A1.65 1.65 0 0 0 9 4.6h.08A1.65 1.65 0 0 0 10 3.09V3h4v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06 2.83 2.83-.06.06a1.65 1.65 0 0 0-.33 1.82V9c.12.6.65 1 1.26 1H21v4h-.42c-.61 0-1.14.4-1.18 1Z"/></>,
  search: <><circle cx="11" cy="11" r="7"/><path d="m20 20-4-4"/></>, bell: <><path d="M18 8a6 6 0 0 0-12 0c0 7-3 7-3 9h18c0-2-3-2-3-9"/><path d="M10 21h4"/></>,
  chevron: <path d="m9 18 6-6-6-6"/>, download: <><path d="M12 3v12m-5-5 5 5 5-5"/><path d="M5 21h14"/></>, spark: <path d="m12 3 1.5 4.5L18 9l-4.5 1.5L12 15l-1.5-4.5L6 9l4.5-1.5L12 3Zm6 11 .8 2.2L21 17l-2.2.8L18 20l-.8-2.2L15 17l2.2-.8L18 14Z"/>,
  up: <path d="m18 15-6-6-6 6"/>, down: <path d="m6 9 6 6 6-6"/>, clock: <><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3 2"/></>, check: <path d="m5 12 4 4L19 6"/>, database: <><ellipse cx="12" cy="5" rx="8" ry="3"/><path d="M4 5v6c0 1.7 3.6 3 8 3s8-1.3 8-3V5M4 11v6c0 1.7 3.6 3 8 3s8-1.3 8-3v-6"/></>, arrow: <><path d="M5 12h14M14 7l5 5-5 5"/></>
}

function Icon({ name, size = 18 }: { name: IconName; size?: number }) {
  return <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">{paths[name]}</svg>
}

const trendData = {
  '30 days': [72, 76, 79, 78, 84, 82, 87, 89, 86, 92, 95, 97],
  '90 days': [62, 65, 67, 69, 68, 72, 76, 81, 80, 87, 91, 97],
  '12 months': [48, 52, 50, 59, 63, 66, 71, 74, 79, 85, 89, 97],
}

const activity = [
  { icon: 'users' as IconName, color: 'blue', title: 'Member eligibility synchronized', detail: '12,481 records · Member Management', time: '2 min ago' },
  { icon: 'card' as IconName, color: 'violet', title: 'Enrollment batch validated', detail: '4,208 records · Enrollment', time: '8 min ago' },
  { icon: 'pill' as IconName, color: 'coral', title: 'Pharmacy claims ingested', detail: '28,940 records · PBM', time: '14 min ago' },
  { icon: 'shield' as IconName, color: 'green', title: 'Compliance audit completed', detail: 'No exceptions detected · All domains', time: '1 hr ago' },
]

const insights = [
  { tag: 'COST OPPORTUNITY', tone: 'amber', title: 'Specialty drug spend increased 18.4%', copy: 'Driven by 3 therapeutic classes across the Midwest region.', value: '$1.2M', label: 'potential annual savings', action: 'View cost drivers' },
  { tag: 'MEMBER RISK', tone: 'red', title: '1,247 members at risk of lapse', copy: 'Premium grace periods expire within the next 14 days.', value: '82%', label: 'reachable via digital', action: 'Review population' },
]

function Sparkline({ values, positive = true }: { values: number[]; positive?: boolean }) {
  const points = values.map((v, i) => `${i * (120 / (values.length - 1))},${38 - (v - Math.min(...values)) * (30 / Math.max(1, Math.max(...values) - Math.min(...values)))}`).join(' ')
  return <svg className="sparkline" viewBox="0 0 120 42" preserveAspectRatio="none"><polyline points={points} fill="none" stroke={positive ? '#167b68' : '#d65f4a'} strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round"/></svg>
}

function App() {
  const [range, setRange] = useState<keyof typeof trendData>('30 days')
  const [plan, setPlan] = useState('All plans')
  const [activeNav, setActiveNav] = useState('Overview')
  const [queryOpen, setQueryOpen] = useState(false)
  const [notice, setNotice] = useState('')
  const values = useMemo(() => trendData[range], [range])
  const chartPoints = values.map((v, i) => `${24 + i * 52},${190 - (v - 45) * 2.55}`).join(' ')

  const notify = (message: string) => { setNotice(message); window.setTimeout(() => setNotice(''), 2400) }

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div className="brand"><div className="brand-mark"><Icon name="pulse" size={21}/></div><span>Clarity Health</span></div>
        <nav>
          <p className="nav-label">WORKSPACE</p>
          {[
            ['Overview','grid'], ['Members','users'], ['Enrollment','card'], ['Pharmacy','pill'], ['Data health','pulse']
          ].map(([label, icon]) => <button key={label} className={activeNav === label ? 'nav-item active' : 'nav-item'} onClick={() => { setActiveNav(label); if (label !== 'Overview') notify(`${label} workspace selected`) }}><Icon name={icon as IconName}/><span>{label}</span>{label === 'Data health' && <i className="health-dot"/>}</button>)}
          <p className="nav-label admin">ADMINISTRATION</p>
          <button className="nav-item" onClick={() => notify('Compliance center selected')}><Icon name="shield"/><span>Compliance</span></button>
          <button className="nav-item" onClick={() => notify('Settings selected')}><Icon name="settings"/><span>Settings</span></button>
        </nav>
        <div className="security-card"><div className="security-icon"><Icon name="shield" size={17}/></div><div><strong>HIPAA compliant</strong><span>Controls are active</span></div><i className="status-dot"/></div>
        <div className="sidebar-footer"><div className="avatar">PN</div><div><strong>Priya N.</strong><span>Executive administrator</span></div><Icon name="chevron" size={15}/></div>
      </aside>

      <main>
        <header className="topbar">
          <div className="search"><Icon name="search" size={17}/><input aria-label="Search" placeholder="Search members, claims, insights…"/><kbd>⌘ K</kbd></div>
          <div className="header-actions"><button className="icon-btn" aria-label="Notifications" onClick={() => notify('No new critical alerts')}><Icon name="bell"/><i/></button><span className="divider"/><button className="help" onClick={() => notify('Help center opened')}>?</button></div>
        </header>

        <div className="content">
          <div className="page-head"><div><p className="eyebrow">EXECUTIVE OVERVIEW</p><h1>Good morning, Priya.</h1><p>Here’s what’s happening across your health plan today.</p></div><div className="page-actions"><button className="secondary" onClick={() => notify('Executive brief exported securely')}><Icon name="download" size={16}/> Export brief</button><button className="primary" onClick={() => setQueryOpen(true)}><Icon name="spark" size={16}/> Ask Clarity AI</button></div></div>

          <section className="freshness"><div className="fresh-left"><span className="live-dot"/><strong>All systems operational</strong><span>Data refreshed 2 minutes ago</span></div><div className="sources"><span><i className="source-dot blue"/>Member Management <b>Live</b></span><span><i className="source-dot violet"/>Enrollment <b>Live</b></span><span><i className="source-dot coral"/>PBM <b>Live</b></span></div></section>

          <section className="metrics">
            <article><div className="metric-top"><span className="metric-icon blue"><Icon name="users"/></span><span className="delta positive"><Icon name="up" size={12}/> 2.4%</span></div><p>Total covered lives</p><div className="metric-bottom"><strong>842,619</strong><Sparkline values={[5,8,7,12,14,13,19,20]}/></div><small>vs. previous period</small></article>
            <article><div className="metric-top"><span className="metric-icon violet"><Icon name="card"/></span><span className="delta positive"><Icon name="up" size={12}/> 1.8%</span></div><p>Active enrollment</p><div className="metric-bottom"><strong>96.7%</strong><Sparkline values={[8,7,11,10,14,16,17,19]}/></div><small>815,174 active members</small></article>
            <article><div className="metric-top"><span className="metric-icon coral"><Icon name="pill"/></span><span className="delta negative"><Icon name="up" size={12}/> 4.1%</span></div><p>Pharmacy spend PMPM</p><div className="metric-bottom"><strong>$128.42</strong><Sparkline positive={false} values={[8,10,9,15,13,18,21,23]}/></div><small>vs. $123.36 last period</small></article>
            <article><div className="metric-top"><span className="metric-icon green"><Icon name="pulse"/></span><span className="delta positive"><Icon name="up" size={12}/> 0.6%</span></div><p>Data quality score</p><div className="metric-bottom"><strong>98.4%</strong><Sparkline values={[10,9,13,12,15,16,18,20]}/></div><small>Across all source systems</small></article>
          </section>

          <section className="main-grid">
            <article className="panel trend-panel"><div className="panel-head"><div><h2>Covered lives trend</h2><p>Active membership across all lines of business</p></div><div className="filters"><select aria-label="Plan filter" value={plan} onChange={e => setPlan(e.target.value)}><option>All plans</option><option>Commercial</option><option>Medicare</option><option>Medicaid</option></select><select aria-label="Date range" value={range} onChange={e => setRange(e.target.value as keyof typeof trendData)}><option>30 days</option><option>90 days</option><option>12 months</option></select></div></div><div className="chart-wrap"><div className="y-axis"><span>860K</span><span>840K</span><span>820K</span><span>800K</span><span>780K</span></div><svg className="line-chart" viewBox="0 0 620 220" preserveAspectRatio="none"><defs><linearGradient id="area" x1="0" y1="0" x2="0" y2="1"><stop offset="0" stopColor="#176f67" stopOpacity=".2"/><stop offset="1" stopColor="#176f67" stopOpacity="0"/></linearGradient></defs>{[20,62,104,146,188].map(y => <line key={y} x1="20" x2="600" y1={y} y2={y} stroke="#e8ece9" strokeDasharray="4 5"/>)}<polygon points={`24,205 ${chartPoints} 596,205`} fill="url(#area)"/><polyline points={chartPoints} fill="none" stroke="#176f67" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round"/><circle cx="596" cy={190 - (values.at(-1)! - 45) * 2.55} r="5" fill="white" stroke="#176f67" strokeWidth="3"/></svg><div className="x-axis"><span>May 20</span><span>May 26</span><span>Jun 1</span><span>Jun 7</span><span>Jun 13</span><span>Today</span></div></div><div className="chart-summary"><span><i/>Current <b>{plan === 'All plans' ? '842,619' : plan === 'Commercial' ? '411,082' : plan === 'Medicare' ? '238,104' : '193,433'}</b></span><span>Net change <b className="green-text">+19,482</b></span><span>Average daily <b>+649</b></span></div></article>

            <article className="panel insights-panel"><div className="panel-head"><div><h2>Clarity AI insights</h2><p>Prioritized opportunities from unified data</p></div><span className="ai-badge"><Icon name="spark" size={13}/> AI POWERED</span></div><div className="insight-list">{insights.map(item => <div className="insight" key={item.title}><div className={`insight-tag ${item.tone}`}>{item.tag}</div><h3>{item.title}</h3><p>{item.copy}</p><div className="insight-value"><strong>{item.value}</strong><span>{item.label}</span></div><button onClick={() => notify(`${item.action} opened`)}>{item.action}<Icon name="arrow" size={14}/></button></div>)}</div><button className="view-all" onClick={() => notify('All insights loaded')}>View all insights <Icon name="arrow" size={14}/></button></article>
          </section>

          <section className="bottom-grid">
            <article className="panel activity-panel"><div className="panel-head"><div><h2>Live data activity</h2><p>Latest events across connected systems</p></div><button onClick={() => notify('Data health workspace selected')}>View data health <Icon name="arrow" size={14}/></button></div><div>{activity.map(a => <div className="activity-row" key={a.title}><span className={`activity-icon ${a.color}`}><Icon name={a.icon} size={16}/></span><div><strong>{a.title}</strong><span>{a.detail}</span></div><time><Icon name="clock" size={13}/>{a.time}</time></div>)}</div></article>
            <article className="panel compliance-panel"><div className="panel-head"><div><h2>Compliance posture</h2><p>Real-time control monitoring</p></div><span className="compliant"><Icon name="check" size={13}/> COMPLIANT</span></div><div className="compliance-score"><div className="score-ring"><strong>100</strong><span>/ 100</span></div><div><strong>All safeguards active</strong><p>Last full audit: June 18, 2026</p></div></div><div className="control-grid"><span><Icon name="shield" size={15}/> PHI encryption<b>Active</b></span><span><Icon name="users" size={15}/> Access controls<b>Enforced</b></span><span><Icon name="database" size={15}/> Audit logging<b>Complete</b></span><span><Icon name="clock" size={15}/> Data retention<b>Compliant</b></span></div><button className="compliance-link" onClick={() => notify('Compliance report opened')}>View compliance report <Icon name="arrow" size={14}/></button></article>
          </section>
        </div>
      </main>

      {queryOpen && <div className="modal-backdrop" onMouseDown={() => setQueryOpen(false)}><div className="query-modal" onMouseDown={e => e.stopPropagation()}><div className="modal-icon"><Icon name="spark"/></div><h2>Ask your unified payer data</h2><p>Answers are generated from de-identified, role-authorized data.</p><div className="query-box"><textarea autoFocus placeholder="e.g. What is driving pharmacy cost growth in the Midwest?"/><button onClick={() => { setQueryOpen(false); notify('Secure query submitted') }}><Icon name="arrow"/></button></div><div className="suggestions"><button>Compare enrollment by plan</button><button>Show lapse risk</button><button>Analyze specialty spend</button></div><small><Icon name="shield" size={13}/> PHI-safe · Every query is logged</small></div></div>}
      {notice && <div className="toast"><Icon name="check" size={15}/>{notice}</div>}
    </div>
  )
}

export default App
